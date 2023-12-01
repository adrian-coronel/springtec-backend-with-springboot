package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ProfessionAvailabilityDto;

import java.util.Set;

public interface IProfessionAvailabilityService {

   Set<ProfessionAvailabilityDto> findAllByTechnical(Integer technicalId) throws ElementNotExistInDBException;
   ProfessionAvailabilityDto findById(Integer professionAvailabilityId) throws ElementNotExistInDBException;
   ProfessionAvailabilityDto findByTechnicalIdAndAvailabilityIdAndProfessionId(Integer technicalId, Integer availabilityId, Integer professionId);
   ProfessionAvailabilityDto save(Integer technicalId, ProfessionAvailabilityDto professionAvailabilityDto) throws Exception;
   ProfessionAvailabilityDto update(Integer professionAvailabilityId, ProfessionAvailabilityDto professionAvailabilityDto) throws Exception ;

   void delete(Integer professionAvailabilityId) throws ElementNotExistInDBException;

}
