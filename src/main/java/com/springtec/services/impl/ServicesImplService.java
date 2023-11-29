package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
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

@Service
@RequiredArgsConstructor
public class ServicesImplService implements IServicesService {

   private final StorageService storageService;
   private final ImageServiceRepository imageServiceRepository;
   private final ServiceRepository serviceRepository;
   private final CurrencyTypeRepository currencyTypeRepository;
   private final CategoryServiceRepository categoryServiceRepository;
   private final ServiceTypeAvailabilityRepository serviceTypeAvailabilityRepository;
   private final ProfessionAvailabilityRepository professionAvailabilityRepository;


   @Override
   public List<ServiceDto> findByFilters(Map<String, String> filters) {
      return null;
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
   public ServiceDto save(ServiceRequest serviceRequest) throws ElementNotExistInDBException {
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
      ProfessionAvailability professionAvailability = professionAvailabilityRepository.findByTechnicalIdAndAvailabilityIdAndProfessionId(
                  serviceRequest.getTechnicalId(), serviceRequest.getAvailabilityId(), serviceRequest.getProfessionId() );

      if (professionAvailability == null){
         throw new ElementNotExistInDBException("ProfessionAvaialility no exisste, ");
      }

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

   private List<ServiceDto> mapServiceToServiceDto(List<Services> servicesList){
      return servicesList
          .stream()
          .map(ServiceDto::new)
          .toList();
   }



}
