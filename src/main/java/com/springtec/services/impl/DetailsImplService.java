package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DetailsTechnicalDto;
import com.springtec.models.entity.Availability;
import com.springtec.models.entity.DetailsTechnical;
import com.springtec.models.entity.Experience;
import com.springtec.models.entity.Profession;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.DetailsTechnicalRepository;
import com.springtec.models.repositories.TechnicalRepository;
import com.springtec.services.IDetailsTechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


   @Override
   public DetailsTechnicalDto update(Integer technicalId, Integer detailTechnicalId, DetailsTechnicalDto detailsTechnicalDto) throws ElementNotExistInDBException {
      if (!technicalRepository.existsByIdAndUserState(technicalId, State.ACTIVE))
         throw new ElementNotExistInDBException("Tecnico no encontrado o ya se encuentra inactivo");
      if (!detailsTechnicalRepository.existsById(detailTechnicalId))
         throw new ElementNotExistInDBException("DetailTechnical no encontrado o ya se encuentra inactivo");

      DetailsTechnical detailsTechnical = detailsTechnicalRepository.findByIdAndTechnicalId(detailTechnicalId, technicalId);
      changeNewValues(detailsTechnicalDto, detailsTechnical);

      // GUARDAMOS LOS CAMBIOS
      DetailsTechnical detailsTechnicalUpdated = detailsTechnicalRepository.save(detailsTechnical);

      return new DetailsTechnicalDto(detailsTechnicalUpdated);
   }

   @Override
   public List<DetailsTechnicalDto> updateAll(Integer technicalId, List<DetailsTechnicalDto> detailsTechnicalDtoList) throws ElementNotExistInDBException {
      if (!technicalRepository.existsByIdAndUserState(technicalId, State.ACTIVE))
         throw new ElementNotExistInDBException("Tecnico no encontrado o ya se encuentra inactivo");

      List<DetailsTechnical> detailsSaved = new ArrayList<>();
      detailsTechnicalDtoList.forEach(detail -> {
         DetailsTechnical dT = detailsTechnicalRepository.findByIdAndTechnicalId(detail.getId(), technicalId);
         changeNewValues(detail, dT);
         detailsSaved.add(dT);
      });
      // Guarda todos y luego retornará todos los actualizados como DTO
      return detailsTechnicalRepository.saveAll(detailsSaved).stream()
          .map(DetailsTechnicalDto::new)
          .toList();
   }

   private void changeNewValues(DetailsTechnicalDto detail, DetailsTechnical dT) {
      dT.setAvailability( Availability.builder().id(detail.getAvailabilityId()).build() );
      dT.setProfession( Profession.builder().id(detail.getProfessionId()).build() );
      dT.setExperience(Experience.builder().id(detail.getExperienceId()).build());
      dT.setLatitude(detail.getLatitude());
      dT.setLongitude(detail.getLongitude());
   }


}
