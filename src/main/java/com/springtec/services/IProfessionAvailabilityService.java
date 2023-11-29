package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ProfessionAvailabilityDto;
import com.springtec.models.entity.ProfessionAvailability;
import com.springtec.models.entity.Technical;

import java.util.List;
import java.util.Set;

public interface IProfessionAvailabilityService {

   Set<ProfessionAvailabilityDto> findAllByTechnical(Integer technicalId) throws ElementNotExistInDBException;
   ProfessionAvailabilityDto findById(Integer professionAvailabilityId) throws ElementNotExistInDBException;
   ProfessionAvailabilityDto save(Integer technicalId, ProfessionAvailabilityDto professionAvailabilityDto) throws Exception;
   ProfessionAvailabilityDto update(Integer professionAvailabilityId, ProfessionAvailabilityDto professionAvailabilityDto) throws Exception ;

   Set<ProfessionAvailabilityDto> findAllByTechnicalAndProfessionId(Integer technicalId,Integer professionId);


   void delete(Integer professionAvailabilityId) throws ElementNotExistInDBException;
}
