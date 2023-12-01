package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.exceptions.InvalidArgumentException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.dto.ImageUploadDto;
import com.springtec.models.dto.ProfessionAvailabilityDto;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.payload.DirectRequestRequest;
import com.springtec.models.repositories.*;
import com.springtec.services.IDirectRequestService;
import com.springtec.services.IProfessionAvailabilityService;
import com.springtec.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DirectRequestImplService implements IDirectRequestService {

   private final DirectRequestRepository directRequestRepository;
   private final ProfessionAvailabilityRepository professionAvailabilityRepository;
   private final IProfessionAvailabilityService professionAvailabilityService;
   private final ServiceTypeAvailabilityRepository serviceTypeAvailabilityRepository;
   private final StateDirectRequestRepository stateDirectRequestRepository;
   private final ImageUploadRepository imageUploadRepository;
   private final TechnicalRepository technicalRepository;
   private final StorageService storageService;
   private final ClientRepository clientRepository;


   @Override
   public List<DirectRequestDto> findAllFiltersByTechnical(Map<String, String> filters) throws Exception {
      //todo OPTIMIZAR
      filterExceptions(filters);

      int technicalId = Integer.parseInt(filters.get("technicalId"));
      List<DirectRequest> directRequestList = new ArrayList<>();
      if (filters.containsKey("technicalId") && filters.containsKey("state")){
         directRequestList = directRequestRepository.findAllByTechnicalIdAndState(technicalId, Integer.parseInt(filters.get("state")));
      }
      else if (filters.containsKey("technicalId")) {
         directRequestList = directRequestRepository.findAllByTechnicalIdAndDistintState(technicalId, State.CANCELED);
      }

      // Retornamos la lista de DirectRequest mapeada a DTO y con sus respectivas imagenes
      return directRequestList.stream().map(directRequest -> {
         List<ImageUploadDto> filesByDirectRequest = getFilesByDirectRequest(directRequest);
         try {
            ProfessionAvailabilityDto professionAvailabilityDto = professionAvailabilityService.findById(directRequest.getProfessionAvailability().getId());
            return new DirectRequestDto(directRequest, filesByDirectRequest, professionAvailabilityDto);
         } catch (ElementNotExistInDBException e) {
            throw new RuntimeException(e);
         }
      }).toList();
   }

   private void filterExceptions(Map<String, String> filters) throws Exception {

      if (!filters.containsKey("technicalId"))
         throw new InvalidArgumentException("El id del tecnico es requerido o no existe");
      if (!technicalRepository.existsByIdAndUserState(Integer.parseInt(filters.get("technicalId")),State.ACTIVE))
         throw new ElementNotExistInDBException("El tecnico no existe o es inactivo");
   }

   @Override
   public DirectRequestDto findById(Integer id) throws Exception {
      // Obtenemos el directRequest por su ID
      DirectRequest directRequest = directRequestRepository.findById(id)
          .orElseThrow(() -> new ElementNotExistInDBException("DirectRequest con id "+id+" no existe."));

      // Obtenemos todas las archivos de cada directrequest preparadas para ser enviados
      List<ImageUploadDto> filesByDirectRequest = getFilesByDirectRequest(directRequest);
      ProfessionAvailabilityDto professionAvailabilityDto = professionAvailabilityService.findById(directRequest.getProfessionAvailability().getId());

      return new DirectRequestDto(directRequest, filesByDirectRequest, professionAvailabilityDto);
   }

   @Override
   public DirectRequestDto save(DirectRequestRequest directRequest) throws ElementNotExistInDBException {
      ProfessionAvailability professionAvailability = professionAvailabilityRepository.findById(directRequest.getProfessionAvailabilityId())
          .orElseThrow(()-> new ElementNotExistInDBException("ProfessionAvailability con id "+directRequest.getProfessionAvailabilityId()+" no existe."));
      Client client = clientRepository.findById(directRequest.getClientId())
          .orElseThrow(()-> new ElementNotExistInDBException("Cliente con id "+directRequest.getClientId()+" no existe."));

      ServiceTypeAvailability serviceTypeAvailability = null;
      // Verifica si el ID de serviceType no es null
      if (directRequest.getServiceTypeAvailabilityId() != null)
         serviceTypeAvailability = serviceTypeAvailabilityRepository.findById(directRequest.getServiceTypeAvailabilityId())
             .orElseThrow(() -> new ElementNotExistInDBException("ServiceTypeAvailability con id "+directRequest.getServiceTypeAvailabilityId()+" no existe."));

      DirectRequest directRequestSaved = directRequestRepository.save(
          DirectRequest.builder()
              .professionAvailability(professionAvailability)
              .serviceTypeAvailability(serviceTypeAvailability)
              .client(client)
              .latitude(directRequest.getLatitude())
              .longitude(directRequest.getLongitude())
              .title(directRequest.getTitle())
              .description(directRequest.getDescription())
              .stateDirectRequest(
                  StateDirectRequest.builder()
                      .id(State.PENDING)
                      .build()
              )
              .build()
      );

      if (!directRequest.getImageUrls().isEmpty()) {
         directRequest.getImageUrls().forEach(file -> {
            String originalFileName = file.getOriginalFilename();
            String fileNameEncryptedSaved = storageService.store(file);

            imageUploadRepository.save(
                ImageUpload.builder()
                    .directRequest(directRequestSaved)
                    .originalName( storageService.getFileName(originalFileName) )
                    .extensionName( storageService.getFileExtension(originalFileName) )
                    .contentType( file.getContentType() )
                    .fakeName( storageService.getFileName( fileNameEncryptedSaved ))
                    .fakeExtensionName( storageService.getFileExtension( fileNameEncryptedSaved ) )
                    .build()
            );
         });
      }

      return new DirectRequestDto(directRequestSaved);
   }

   @Override
   public DirectRequestDto changeState(Integer id, DirectRequestRequest directRequestRequest) throws ElementNotExistInDBException {
      DirectRequest directRequestRequestFind = directRequestRepository.findById(id)
          .orElseThrow(() -> new ElementNotExistInDBException("DirectRequest con id "+id+" no existe."));
      StateDirectRequest state = stateDirectRequestRepository.findById(directRequestRequest.getStateId())
          .orElseThrow(() -> new ElementNotExistInDBException("StateDirectRequest con id "+id+" no existe."));

      directRequestRequestFind.setStateDirectRequest(state);

      return new DirectRequestDto(directRequestRepository.save(directRequestRequestFind));
   }


   /**
    * Obtenemos todas los archivos en array de bytes para cada DirectRequest
    * */
   private List<ImageUploadDto> getFilesByDirectRequest(DirectRequest directRequest) {
      return imageUploadRepository.findAllByDirectRequestId(directRequest.getId())
          .stream()
          .parallel()
          .map(this::mapImageUploadToDto)
          .toList();
   }

   private ImageUploadDto mapImageUploadToDto(ImageUpload imageUpload) {
      String fakeFileName = imageUpload.getFakeName() + "." + imageUpload.getFakeExtensionName();
      String originalFileName = imageUpload.getOriginalName() + "." + imageUpload.getExtensionName();

      try {
         byte[] imageInBytes = storageService.loadAsDecryptedFile(fakeFileName, originalFileName);
         return ImageUploadDto.builder()
             .fileName(originalFileName)
             .contentType(imageUpload.getContentType())
             .file(imageInBytes)
             .build();
      } catch (IOException e) {
         throw new RuntimeException("Error al cargar el archivo", e);
      }
   }


}
