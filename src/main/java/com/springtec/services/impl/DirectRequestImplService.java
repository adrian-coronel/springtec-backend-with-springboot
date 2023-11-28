package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.exceptions.StorageException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.dto.ImageUploadDto;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.payload.DirectRequestRequest;
import com.springtec.models.repositories.*;
import com.springtec.services.IDirectRequestService;
import com.springtec.storage.FileEncryptor;
import com.springtec.storage.FileInfo;
import com.springtec.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectRequestImplService implements IDirectRequestService {

   private final DirectRequestRepository directRequestRepository;
   private final ProfessionAvailabilityRepository professionAvailabilityRepository;
   private final ServiceTypeAvailabilityRepository serviceTypeAvailabilityRepository;
   private final ImageUploadRepository imageUploadRepository;
   private final TechnicalRepository technicalRepository;
   private final StorageService storageService;
   private final ClientRepository clientRepository;


   @Override
   public List<DirectRequestDto> findAllActivesByTechnicalId(Integer technicalId) throws ElementNotExistInDBException {
      if (technicalRepository.existsByIdAndUserState(technicalId, State.ACTIVE))
         throw new ElementNotExistInDBException("El tecnico con el id "+technicalId+" no existe.");

      // DEVOLVER LOS DATOS DE LA PROFESSION AVAILABILITY DTO SIN EL TECNICO
      List<DirectRequest> allDirectRequestActives = directRequestRepository.findByProfessionAvailabilityTechnicalIdAndState(technicalId, State.ACTIVE);
      // BUSCAR UN RECURSO CON EL FAKE FAILNAME DE LA BD
      allDirectRequestActives.forEach(directRequest -> {
         // Buscamos todos los registros de imagenes por directRequest
         imageUploadRepository.findAllByDirectRequestId(directRequest.getId());
      });
      // CAMBIAR EL NOMBRE Y EXTENSION DEL FILE A SU ORIGINAL FILENAME
      return null;
   }

   @Override
   public DirectRequestDto findById(Integer id) throws Exception {
      // Obtenemos el directRequest por su ID
      DirectRequest directRequest = directRequestRepository.findById(id)
          .orElseThrow(() -> new ElementNotExistInDBException("DirectRequest con id "+id+" no existe."));

      // Obtenemos todos los ImageUploads del DIRECT REQUEST
      List<ImageUpload> imageUploadList = imageUploadRepository.findAllByDirectRequestId(directRequest.getId());

      // CARGAMOS TODOS LAS IMAGENES GUARDADOS POR DIRECT REQUEST
      List<ImageUploadDto> filesByDirectRequest = imageUploadList.stream().map(imageUpload ->{
         String fakeFileName = imageUpload.getFakeName()+"."+imageUpload.getFakeExtensionName();
         String originalFileName = imageUpload.getOriginalName()+"."+imageUpload.getExtensionName();

         //todo RETORNAR EL ARCHIVO EN BYTES
         try {
            byte[] imageInBytes = storageService.loadAsDecryptedFile(fakeFileName, originalFileName);
            return ImageUploadDto.builder()
                .fileName(originalFileName)
                .contentType(imageUpload.getContentType())
                .file(imageInBytes)
                .build();
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }).toList();

      return new DirectRequestDto(directRequest, filesByDirectRequest);
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
                    .contentType( file.getContentType() )
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
