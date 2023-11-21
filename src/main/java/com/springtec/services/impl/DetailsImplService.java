package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DetailsTechnicalDto;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.DetailsTechnicalRepository;
import com.springtec.models.repositories.TechnicalRepository;
import com.springtec.services.IDetailsTechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetailsImplService implements IDetailsTechnicalService {

   private final DetailsTechnicalRepository detailsTechnicalRepository;
   private final TechnicalRepository technicalRepository;

   @Override
   public List<DetailsTechnicalDto> findAll(Integer technicalId) throws ElementNotExistInDBException {
      if (!technicalRepository.existsByIdAndUserState(technicalId, State.ACTIVE))
         throw new ElementNotExistInDBException("Tenico no encontrado o ya se encuentra inactivo");

      return detailsTechnicalRepository.findAllByTechnicalIdAndState(technicalId, State.ACTIVE)
          .stream()
          .map(DetailsTechnicalDto::new)
          .toList();
   }
}
