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
              //todo GUARDAR EL TYPE_ SERVICIO DISPONIBLE
          .latitude(directRequest.getLatitude())
          .longitude(directRequest.getLongitude())
          .title(directRequest.getTitle())
          .description(directRequest.getDescription())
          .state(State.ACTIVE)
          .build()
      );
      //todo GUARDAR TODAS LAS IMAGENES UPLOADS

      directRequestSaved.getTechnical().setUser(null);
      return DirectRequestDto.builder()
          .id(directRequestSaved.getId())
          .technicalDto(new TechnicalDto(directRequestSaved.getTechnical()))
          .clientDto(new ClientDto(directRequestSaved.getClient()))
           //TODO REETORNAR EL TYPO DE SERVICIO DISPONIBLE
          .latitude(directRequestSaved.getLatitude())
          .longitude(directRequestSaved.getLongitude())
          .title(directRequestSaved.getTitle())
          .description(directRequestSaved.getDescription())
          //todo ENVIAR LAS IMAGENES UPLOADS
          .build();

   }
}
