package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ClientDto;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.dto.ServiceDto;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.payload.DirectRequestRequest;
import com.springtec.models.repositories.*;
import com.springtec.services.IDirectRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectRequestImplService implements IDirectRequestService {

   private final DirectRequestRepository directRequestRepository;
   private final TechnicalRepository technicalRepository;
   private final ClientRepository clientRepository;
   private final ServiceRepository serviceRepository;
   private final ImgFirebaseRepository imgFirebaseRepository;

   @Override
   public DirectRequestDto save(DirectRequestRequest directRequest) throws ElementNotExistInDBException {
      System.out.println(directRequest);
      Technical technical = technicalRepository.findById(directRequest.getTechnicalId())
          .orElseThrow(()-> new ElementNotExistInDBException("Tecnico con id "+directRequest.getTechnicalId()+" no existe."));
      Client client = clientRepository.findById(directRequest.getClientId())
          .orElseThrow(()-> new ElementNotExistInDBException("Cliente con id "+directRequest.getClientId()+" no existe."));

      Services service = null;
      if (directRequest.getServiceId()!= null){
         service = serviceRepository.findById(directRequest.getServiceId())
             .orElseThrow(()-> new ElementNotExistInDBException("Servicio con id "+directRequest.getServiceId()+" no existe."));
      }


      // GUARDAMOS LOS DATOS DE LA SOLICITUD DIRECTA
      DirectRequest directRequestSaved = directRequestRepository.save(
          DirectRequest.builder()
          .technical(technical)
          .client(client)
          .service(service)
          .latitude(directRequest.getLatitude())
          .longitude(directRequest.getLongitude())
          .title(directRequest.getTitle())
          .description(directRequest.getDescription())
          .state(State.ACTIVE)
          .build()
      );
      List<ImgFirebase> imgFirebaseList = new ArrayList<>();
      directRequest.getImageUrls().forEach(url -> {
         imgFirebaseList.add(imgFirebaseRepository.save(new ImgFirebase(directRequestSaved.getId(), url)));
      });

      directRequestSaved.getTechnical().setUser(null);
      return DirectRequestDto.builder()
          .id(directRequestSaved.getId())
          .technicalDto(new TechnicalDto(directRequestSaved.getTechnical()))
          .clientDto(new ClientDto(directRequestSaved.getClient()))
          .serviceDto( directRequest.getServiceId() != null ?
              new ServiceDto(directRequestSaved.getService())
              : null
          )
          .latitude(directRequestSaved.getLatitude())
          .longitude(directRequestSaved.getLongitude())
          .title(directRequestSaved.getTitle())
          .description(directRequestSaved.getDescription())
          .imageUrls(imgFirebaseList)
          .build();

   }
}
