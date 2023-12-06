package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.exceptions.InvalidArgumentException;
import com.springtec.models.dto.ImageUploadDto;
import com.springtec.models.dto.ServiceDto;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.payload.ServiceRequest;
import com.springtec.models.repositories.*;
import com.springtec.services.IServicesService;
import com.springtec.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServicesImplService implements IServicesService {

   private final StorageService storageService;
   private final TechnicalRepository technicalRepository;
   private final ProfessionRepository professionRepository;
   private final ImageServiceRepository imageServiceRepository;
   private final ServiceRepository serviceRepository;
   private final CurrencyTypeRepository currencyTypeRepository;
   private final CategoryServiceRepository categoryServiceRepository;
   private final ServiceTypeAvailabilityRepository serviceTypeAvailabilityRepository;
   private final ProfessionAvailabilityRepository professionAvailabilityRepository;

   @Override
   public List<ServiceDto> findByFilters(Map<String, String> filters) throws Exception {
      filterException(filters);
      List<Services> servicesList = null;
      if (filters.containsKey("categoryId")  && filters.containsKey("technicalId") && filters.containsKey("professionId")){
         servicesList = serviceRepository.findAllByTechnicalIdAndProfessionIdAndCategoryId(
             Integer.parseInt(filters.get("technicalId")),
             Integer.parseInt(filters.get("professionId")),
             Integer.parseInt(filters.get("categoryId"))
         );
      } else {
         throw new InvalidArgumentException("Filtro no soportado");
      }
      // Traer el availability amarrado al servicio

      return servicesList.stream()
          .map(service -> {
             try {
                return findById(service.getId());
             } catch (ElementNotExistInDBException e) {
             }
             return null;
          })
          .toList();
   }

   private void filterException(Map<String, String> filters) throws ElementNotExistInDBException {
      if (filters.containsKey("technicalId")
          && !technicalRepository.existsByIdAndUserState(Integer.parseInt(filters.get("technicalId")),State.ACTIVE)
      ){
         throw new ElementNotExistInDBException("El technical con id "+filters.get("technicalId")+" no existe.");
      }
      if (filters.containsKey("categoryId") && !categoryServiceRepository.existsById(Integer.parseInt(filters.get("categoryId")))){
         throw new ElementNotExistInDBException("La categoria con id "+filters.get("categoryId")+" no existe.");
      }
      if (filters.containsKey("professionId") && !professionRepository.existsById(Integer.parseInt(filters.get("professionId")))){
         throw new ElementNotExistInDBException("La profesion con id "+filters.get("professionId")+" no existe.");
      }
   }


   @Override
   public ServiceDto findById(Integer id) throws ElementNotExistInDBException {
      Services service = serviceRepository.findById(id)
          .orElseThrow(() -> new ElementNotExistInDBException("Service con id "+id+" no existe"));

      ImageService imageService = imageServiceRepository.findById(service.getId()).orElse(null);

      ImageUploadDto imageUploadDto;
      if (imageService != null){
         String fakeFileName = imageService.getFakeName()+"."+imageService.getFakeExtensionName();
         String originalFileName = imageService.getOriginalName()+"."+imageService.getExtensionName();

         try {
            imageUploadDto = ImageUploadDto.builder()
                    .fileName(originalFileName)
                    .contentType(imageService.getContentType())
                    .file(storageService.loadAsDecryptedFile(fakeFileName, originalFileName))
                    .build();
            // Enviamos el servicio con el file guardado
            return new ServiceDto(service, imageUploadDto);

         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
      // Si el file no existe enviamos solo el service
      return new ServiceDto(service);
   }

   @Override
   public ServiceDto save(ServiceRequest serviceRequest) throws Exception {
      // El archivo no debe ser mayor a 1MB
      if (!serviceRequest.getFile().isEmpty() && serviceRequest.getFile().getSize() > storageService.MAX_SIZE){
         throw new InvalidArgumentException("El archivo pesa "+serviceRequest.getFile().getSize()+" Bytes, no puede exceder los "+storageService.MAX_SIZE+" Bytes");
      }

      CategoryService categoryService = categoryServiceRepository.findById(serviceRequest.getCategoryServiceId())
          .orElseThrow(() -> new ElementNotExistInDBException("CategoryService con id "+serviceRequest.getCategoryServiceId()+" no existe."));
      CurrencyType currencyType = currencyTypeRepository.findById(serviceRequest.getCurrencyTypeId())
          .orElseThrow(() -> new ElementNotExistInDBException("CurrencyType con id "+serviceRequest.getCurrencyTypeId()+" no existe."));

      // PREPARAMOS NUESTRO OBJETO PARA SER GUARDADO
      Services serviceSaved = Services.builder()
          .categoryService(categoryService)
          .currencyType(currencyType)
          .name(serviceRequest.getName())
          .description(serviceRequest.getDescription())
          .price(serviceRequest.getPrice())
          .state(State.ACTIVE)
          .build();
      // GUARDAMOS EL SERVICIO
      serviceSaved = serviceRepository.save(serviceSaved);

      // Buscamos PROFESSION AVAILABILITY que el técnico selecciono
      ProfessionAvailability professionAvailability = professionAvailabilityRepository.findById(serviceRequest.getProfessionAvailabilityId())
          .orElseThrow(() -> new ElementNotExistInDBException("La ProfessionAvailability con id"+serviceRequest.getProfessionAvailabilityId()+" no existe."));

      // GUARDAMOS LA RELACION ENTRE SERVICE - PROFESSION AVAILABILITY
      serviceTypeAvailabilityRepository.save(
          ServiceTypeAvailability.builder()
              .services(serviceSaved)
              .professionAvailability(professionAvailability)
              .build()
      );

      // GUARDAR LOS DATOS DE SERVICE SI ES QUE SE ENVIO
      if (!serviceRequest.getFile().isEmpty()){
         String originalFileName = serviceRequest.getFile().getOriginalFilename();
         // GUARDAR LA IMAGEN EN EL STORAGE
         String fileNameEncryptedSaved = storageService.store(serviceRequest.getFile());

         // GUARDAR LA INFO EN IMG SERVICE (DB)
         imageServiceRepository.save(
             ImageService.builder()
                 .service(serviceSaved)
                 .originalName(storageService.getFileName(originalFileName) )
                 .extensionName(storageService.getFileExtension(originalFileName))
                 .contentType(serviceRequest.getFile().getContentType())
                 .fakeName(storageService.getFileName(fileNameEncryptedSaved))
                 .fakeExtensionName(storageService.getFileExtension(fileNameEncryptedSaved))
                 .build()
         );
      }

      return new ServiceDto(serviceSaved);
   }

   @Override
   public ServiceDto udpate(Integer serviceId, ServiceRequest serviceRequest) throws ElementNotExistInDBException {
      // BUSCAR EL SERVICIO A ACTUALIZAR
      Services service = serviceRepository.findById(serviceId)
          .orElseThrow(() -> new ElementNotExistInDBException("El service con id "+serviceId+" no existe."));

      service.setName(serviceRequest.getName());
      service.setDescription(serviceRequest.getDescription());
      service.setPrice(serviceRequest.getPrice());
      service.setCurrencyType(CurrencyType.builder().id(serviceRequest.getCurrencyTypeId()).build());
      service.setCategoryService(CategoryService.builder().id(serviceRequest.getCategoryServiceId()).build());

      // ACTUALIZAR LA IMAGEN SI ES QUE SE ENVIA
      if (!serviceRequest.getFile().isEmpty()) {
         ImageService imageService = imageServiceRepository.findByServiceId(service.getId());
         String fakeFileName = imageService.getFakeName()+"."+imageService.getFakeExtensionName();
         String originalFileName = imageService.getOriginalName()+"."+imageService.getExtensionName();
         //todo CREAR METODO PARA ELIMINAR Y GUARDAR NUEVA IMAGEN
         //storageService.
      }
      return null;
   }

   private List<ServiceDto> mapServiceToServiceDto(List<Services> servicesList){
      return servicesList
          .stream()
          .map(ServiceDto::new)
          .toList();
   }


}
