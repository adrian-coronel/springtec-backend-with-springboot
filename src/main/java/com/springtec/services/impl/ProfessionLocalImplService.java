package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ProfessionLocalDto;
import com.springtec.models.entity.ProfessionLocal;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.ProfessionAvailabilityRepository;
import com.springtec.models.repositories.ProfessionLocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessionLocalImplService {

   private final ProfessionLocalRepository professionLocalRepository;
   private final ProfessionAvailabilityRepository professionAvailabilityRepository;

   public boolean updateState(Integer professionAvailabilityId, char stateLocal) throws ElementNotExistInDBException {
      if(!(stateLocal == State.ACTIVE || stateLocal == State.INACTIVE))
         throw new IllegalArgumentException("El estado solo de trabajo solo puede contener 0 o 1");
      if (!professionAvailabilityRepository.existsById(professionAvailabilityId))
         throw new ElementNotExistInDBException("La professionAvailability con id "+professionAvailabilityId+" no existe");
      ProfessionLocal professionLocal = professionLocalRepository.findByProfessionAvailabilityId(professionAvailabilityId);

      professionLocal.setState(stateLocal);
      ProfessionLocal professionLocalSaved = professionLocalRepository.save(professionLocal);
      return professionLocalSaved.getState() == State.ACTIVE;
   }

}
