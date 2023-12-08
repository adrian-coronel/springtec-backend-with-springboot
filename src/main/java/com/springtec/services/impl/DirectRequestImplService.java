package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.exceptions.InvalidArgumentException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.dto.ImageUploadDto;
import com.springtec.models.dto.ProfessionAvailabilityDto;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.payload.DirectRequestRequest;
import com.springtec.models.payload.StateRequest;
import com.springtec.models.repositories.*;
import com.springtec.services.IDirectRequestService;
import com.springtec.services.IProfessionAvailabilityService;
import com.springtec.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
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
   private final CategoryServiceRepository categoryServiceRepository;

   @Override
   public List<DirectRequestDto> findAllFiltersByTechnical(Map<String, String> filters) throws Exception {
      //todo OPTIMIZAR
      filterExceptions(filters);

      List<DirectRequest> directRequestList = new ArrayList<>();
      if (filters.containsKey("clientId") && filters.containsKey("state")){
         int clientId = Integer.parseInt(filters.get("clientId"));
         int state = Integer.parseInt(filters.get("state"));
         // Obtenemos la fecha y hora de AYER
         Timestamp oneDaysAgo = Timestamp.valueOf(LocalDateTime.now().minus(Duration.ofDays(1)));
         // Obtenemos los directRequest por estado y que tengan un plazo maximo de un DÍA
         directRequestList = directRequestRepository.findAllByClientIdAndCreatedAtGreaterThanEqualAndStateDirectRequestId(clientId, oneDaysAgo, state);
      }
      else if (filters.containsKey("technicalId") && filters.containsKey("state")){
         int technicalId = Integer.parseInt(filters.get("technicalId"));
         directRequestList = directRequestRepository.findAllByTechnicalIdAndState(technicalId, Integer.parseInt(filters.get("state")));
      }
      else if (filters.containsKey("technicalId")) {
         int technicalId = Integer.parseInt(filters.get("technicalId"));
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

      if (filters.containsKey("technicalId") && !technicalRepository.existsByIdAndUserState(Integer.parseInt(filters.get("technicalId")),State.ACTIVE))
         throw new ElementNotExistInDBException("El tecnico no existe o es inactivo");
      if(filters.containsKey("clientId") && !clientRepository.existsByIdAndUserState(Integer.parseInt(filters.get("clientId")),State.ACTIVE))
         throw new ElementNotExistInDBException("El cliente no existe o es inactivo");
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
   public DirectRequestDto save(DirectRequestRequest directRequest) throws Exception {

      // LOS ARCHIVOS NO PUEDEN SER MAYORES A 1MB
      if (directRequest.getImageUrls() != null && !directRequest.getImageUrls().isEmpty()){
         for (MultipartFile file : directRequest.getImageUrls()) {
            if (file.getSize() > storageService.MAX_SIZE)
               throw new InvalidArgumentException("El archivo '"+file.getOriginalFilename()+"' pesa "+file.getSize()+" Bytes, no puede exceder los "+storageService.MAX_SIZE+" Bytes");
         }
      }

      ProfessionAvailability professionAvailability = professionAvailabilityRepository.findById(directRequest.getProfessionAvailabilityId())
          .orElseThrow(()-> new ElementNotExistInDBException("ProfessionAvailability con id "+directRequest.getProfessionAvailabilityId()+" no existe."));
      Client client = clientRepository.findById(directRequest.getClientId())
          .orElseThrow(()-> new ElementNotExistInDBException("Cliente con id "+directRequest.getClientId()+" no existe."));
      CategoryService categoryService = categoryServiceRepository.findById(directRequest.getCategoryServiceId())
          .orElseThrow(() -> new ElementNotExistInDBException("El categoryService con id "+directRequest.getCategoryServiceId()+" no existe."));

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
              .categoryService(categoryService)
              .latitude(directRequest.getLatitude())
              .longitude(directRequest.getLongitude())
              .title(directRequest.getTitle())
              .description(directRequest.getDescription())
              .stateDirectRequest(
                  StateDirectRequest.builder()
                      .id(State.PENDING)
                      .build()
              )
              .stateInvoice(State.INACTIVE)
              .build()
      );

      if (directRequest.getImageUrls() != null && !directRequest.getImageUrls().isEmpty()) {
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
   public DirectRequestDto changeState(Integer id, StateRequest stateRequest) throws ElementNotExistInDBException {
      DirectRequest directRequestRequestFind = directRequestRepository.findById(id)
          .orElseThrow(() -> new ElementNotExistInDBException("DirectRequest con id "+id+" no existe."));
      StateDirectRequest state = stateDirectRequestRepository.findById(stateRequest.getStateId())
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
