package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ServiceTypeAvailabilityDto;
import com.springtec.models.entity.ServiceTypeAvailability;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.ProfessionRepository;
import com.springtec.models.repositories.ServiceTypeAvailabilityRepository;
import com.springtec.models.repositories.TechnicalRepository;
import com.springtec.services.IProfessionAvailabilityService;
import com.springtec.services.IServiceTypeAvailabilityService;
import com.springtec.services.IServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ServiceTypeAvailabilityImplService implements IServiceTypeAvailabilityService {

   private final TechnicalRepository technicalRepository;
   private final ProfessionRepository professionRepository;
   private final ServiceTypeAvailabilityRepository serviceTypeAvailabilityRepository;
   private final IProfessionAvailabilityService professionAvailabilityService;
   private final IServicesService servicesService;

   @Override
   public List<ServiceTypeAvailabilityDto> findByFilters(Map<String, String> filters) throws ElementNotExistInDBException {
      filterException(filters);
      
      if(filters.containsKey("technicalId") && filters.containsKey("professionId")) {
         // VERIFICAR SI PROFESSION AVAILABILITY ES TIENE TALLER
          return mapServiceTypeAvailabilityToDto(
               findBYTechnicalIdAndProfessionId(
                  Integer.parseInt(filters.get("technicalId")),
                  Integer.parseInt(filters.get("professionId"))
              )
          );
      }

      return null;
   }

   private void filterException(Map<String, String> filters) throws ElementNotExistInDBException {
      String technicalId = filters.get("technicalId");
      String professionId = filters.get("professionId");
      if (filters.containsKey("technicalId") && !technicalRepository.existsByIdAndUserState(Integer.parseInt(technicalId), State.ACTIVE)){
         throw new ElementNotExistInDBException("El tecnico con el id "+technicalId+" no existe.");
      }
      if (filters.containsKey("professionId") && !professionRepository.existsById(Integer.parseInt(professionId))){
         throw new ElementNotExistInDBException("La profesion con el id "+professionId+" no existe.");
      }
   }

   private List<ServiceTypeAvailabilityDto> mapServiceTypeAvailabilityToDto(List<ServiceTypeAvailability> serviceTypeAvailabilityList){
      return serviceTypeAvailabilityList.stream()
          .map(typeService -> {
             try {
                return new ServiceTypeAvailabilityDto(
                    typeService.getId()
                    , professionAvailabilityService.findById(typeService.getProfessionAvailability().getId())
                    , servicesService.findById(typeService.getServices().getId())
                );
             } catch (ElementNotExistInDBException e) {

             }
             return null;
          }).toList();
   }

   /**
    * Decorador para que sea más entendible la busqueda en ServiceTypeAvailability
    * */
   private List<ServiceTypeAvailability> findBYTechnicalIdAndProfessionId(Integer technicalId, Integer professionId) {
      return serviceTypeAvailabilityRepository.findAllByProfessionAvailabilityTechnicalIdAndProfessionAvailabilityProfessionId(technicalId, professionId);
   }

}
