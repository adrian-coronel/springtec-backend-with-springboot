package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.payload.DirectRequestRequest;
import com.springtec.models.repositories.*;
import com.springtec.services.IDirectRequestService;
import com.springtec.storage.FileEncryptor;
import com.springtec.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectRequestImplService implements IDirectRequestService {

   private final DirectRequestRepository directRequestRepository;
   private final ProfessionAvailabilityRepository professionAvailabilityRepository;
   private final ServiceTypeAvailabilityRepository serviceTypeAvailabilityRepository;
   private final ImageUploadRepository imageUploadRepository;
   private final StorageService storageService;
   private final ClientRepository clientRepository;


   @Override
   public List<DirectRequestDto> findAllByTechnical(Integer technicalId) {
      return null;
   }

   @Override
   public List<DirectRequestDto> findAllActivesByTechnicalId(Integer technicalId) {
      // CARGAR LOS DIRECTREQUEST QUE PERTENECEN AL ID DEL  TECNICO

      // Cargar las imagenes(recursos) que estan encriptados y enviarlo con su nombre y extensión original
      return null;
   }

   @Override
   public DirectRequestDto findById(Integer id) {

      return null;
   }

   @Override
   public DirectRequestDto save(DirectRequestRequest directRequest) throws ElementNotExistInDBException {
      System.out.println(directRequest);
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
              .state(State.ACTIVE)
              .build()
      );

      if (!directRequest.getImageUrls().isEmpty()) {
         directRequest.getImageUrls().forEach(file -> {
            String originalFileName = file.getOriginalFilename();
            String fileNameEncryptedSaved = storageService.store(file);

            imageUploadRepository.save(
                ImageUpload.builder()
                    .directRequest(directRequestSaved)
                    .originalName( getFileName(originalFileName) )
                    .extensionName( getFileExtension(originalFileName) )
                    .fakeName( getFileName( fileNameEncryptedSaved ))
                    .fakeExtensionName( getFileExtension( fileNameEncryptedSaved ) )
                    .build()
            );
         });
      }

      return new DirectRequestDto(directRequestSaved);
   }

   private String getFileName(String fileName) {
      int lastDotIndex = fileName.lastIndexOf(".");
      if (lastDotIndex != -1) {
         return fileName.substring(0, lastDotIndex);
      }
      return fileName; // No hay punto, devolver el nombre completo
   }

   private String getFileExtension(String fileName) {
      int lastDotIndex = fileName.lastIndexOf(".");
      if (lastDotIndex != -1) {
         return fileName.substring(lastDotIndex + 1);
      }
      return ""; // No hay punto, devolver una cadena vacía
   }
}
