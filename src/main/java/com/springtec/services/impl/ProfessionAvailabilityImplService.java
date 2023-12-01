package com.springtec.services.impl;

import com.springtec.exceptions.DuplicateElementException;
import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.exceptions.InvalidArgumentException;
import com.springtec.models.dto.*;
import com.springtec.models.entity.*;
import com.springtec.models.enums.AvailabilityType;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.ProfessionAvailabilityRepository;
import com.springtec.models.repositories.ProfessionLocalRepository;
import com.springtec.models.repositories.TechnicalRepository;
import com.springtec.services.IProfessionAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessionAvailabilityImplService implements IProfessionAvailabilityService {

   private final ProfessionAvailabilityRepository professionAvailabilityRepository;
   private final ProfessionLocalRepository professionLocalRepository;
   private final TechnicalRepository technicalRepository;

   @Override
   public Set<ProfessionAvailabilityDto> findAllByTechnical(Integer technicalId) throws ElementNotExistInDBException {
      if (!technicalRepository.existsByIdAndUserState(technicalId, State.ACTIVE))
        throw new ElementNotExistInDBException("El tecnico con id "+technicalId+" no esta registrado");

      return professionAvailabilityRepository.findAllByTechnicalId(technicalId)
          .stream()
          .map(pA -> {
             ProfessionLocal professionLocal = professionLocalRepository.findByProfessionAvailabilityId(pA.getId());
             if (professionLocal == null)
                return new ProfessionAvailabilityDto(pA);
             return new ProfessionAvailabilityDto(pA, professionLocal.getLatitude(), professionLocal.getLongitude());
          }).collect(Collectors.toSet());
   }

   @Override
   public ProfessionAvailabilityDto findById(Integer professionAvailabilityId) throws ElementNotExistInDBException {
      ProfessionAvailability professionAvailability = professionAvailabilityRepository.findById(professionAvailabilityId)
          .orElseThrow(()-> new ElementNotExistInDBException("ProfessionAvailability con id "+professionAvailabilityId+" no existe"));

      // Si la professionAvailability es TALLER busca en professionLocal
      if (professionAvailability.getAvailability().getId() == AvailabilityType.EN_TALLER_ID){
         ProfessionLocal professionLocal = professionLocalRepository.findByProfessionAvailabilityId(professionAvailabilityId);
         return new ProfessionAvailabilityDto(
             professionAvailability, professionLocal.getLatitude(), professionLocal.getLongitude()
         );
      }
      return new ProfessionAvailabilityDto(professionAvailability);
   }

   @Override
   public ProfessionAvailabilityDto findByTechnicalIdAndAvailabilityIdAndProfessionId(Integer technicalId, Integer availabilityId, Integer professionId) {
      ProfessionAvailability professionAvailability = professionAvailabilityRepository
          .findByTechnicalIdAndAvailabilityIdAndProfessionId(technicalId, availabilityId, professionId);

      // Si la professionAvailability es TALLER busca en professionLocal
      if (professionAvailability.getAvailability().getId() == AvailabilityType.EN_TALLER_ID){
         ProfessionLocal professionLocal = professionLocalRepository.findByProfessionAvailabilityId(professionAvailability.getId());
         return new ProfessionAvailabilityDto(
             professionAvailability, professionLocal.getLatitude(), professionLocal.getLongitude()
         );
      }
      return new ProfessionAvailabilityDto(professionAvailability);
   }


   @Override
   public ProfessionAvailabilityDto save(Integer technicalId, ProfessionAvailabilityDto professionAvailabilityDto) throws Exception {
      // CONDICIONES PARA MANEJAR ERRORES
      boolean availabilityIsLocal = Objects.equals(professionAvailabilityDto.getAvailabilityId(), AvailabilityType.EN_TALLER_ID);
      boolean containLocation = professionAvailabilityDto.getLatitude() != null && professionAvailabilityDto.getLongitude() != null;

      // FILTRAMOS LAS POSIBLES EXCEPTIONS
      filterException(technicalId, professionAvailabilityDto, availabilityIsLocal, containLocation);
      // Buscamos al tecnico
      Technical technical = technicalRepository.findById(technicalId)
          .orElseThrow(() -> new ElementNotExistInDBException("El tecnico con id "+technicalId+" no esta registrado"));

      // CONSTRUIMOS NUESTRA PROFESSIONAVAILABILITY
      ProfessionAvailability professionAvailability = ProfessionAvailability.builder()
          .technical(technical)
          .availability(
              Availability.builder().id(professionAvailabilityDto.getAvailabilityId()).build()
          )
          .profession(
              Profession.builder().id(professionAvailabilityDto.getProfessionId()).build()
          )
          .experience(
              Experience.builder().id(professionAvailabilityDto.getExperienceId()).build()
          )
          .build();

      // GUARDAMOS Y OBTENEMOS EL REGISTRO CON EL ID
      ProfessionAvailability professionAvailabilitySaved = professionAvailabilityRepository.save(professionAvailability);

      // SI HAY UN LOCAL Y LA UBICACION QUE LA GUARDE EN ProfessionLocal
      if (availabilityIsLocal) {
         professionLocalRepository.save(
            ProfessionLocal.builder()
                .professionAvailability(professionAvailabilitySaved)
                .latitude(professionAvailabilityDto.getLatitude())
                .longitude(professionAvailabilityDto.getLongitude())
                .state(State.ACTIVE)
                .build()
         );
      }

      //todo No se esta devolviendo con sus relaciones
      //Actualiza la variable para traer tod el cuerpo de los datos, incluyendo las entidades relacionadas
      professionAvailabilitySaved  = professionAvailabilityRepository.findById(professionAvailabilitySaved.getId())
          .orElseThrow(() -> new RuntimeException("No se pudo recuperar la ProfessionAvailability después de guardar"));
      System.out.println(professionAvailabilitySaved);

      return new ProfessionAvailabilityDto(
          professionAvailabilitySaved,
          professionAvailabilityDto.getLatitude(),
          professionAvailabilityDto.getLongitude());
   }

   @Transactional
   @Override
   public ProfessionAvailabilityDto update(Integer professionAvailabilityId, ProfessionAvailabilityDto professionAvailabilityDto) throws Exception {
      // CONDICIONES PARA MANEJAR ERRORES
      boolean availabilityIsLocal = Objects.equals(professionAvailabilityDto.getAvailabilityId(), AvailabilityType.EN_TALLER_ID);
      boolean containLocation = professionAvailabilityDto.getLatitude() != null && professionAvailabilityDto.getLongitude() != null;

      // FILTRAMOS LAS POSIBLES EXCEPTIONS
      filterException(null, professionAvailabilityDto, availabilityIsLocal, containLocation);

      ProfessionAvailability professionAvailabilityFind = professionAvailabilityRepository.findById(professionAvailabilityId)
          .orElseThrow( () -> new ElementNotExistInDBException("ProfessionAvailability con id "+professionAvailabilityId+" no existe"));

      // ESTA TRATANDO DE CAMBIAR DE DISPONIBLIDAD?
      boolean changeAvailability = professionAvailabilityFind.getAvailability().getId() != professionAvailabilityDto.getAvailabilityId();
      if (changeAvailability){
         // Si el cambio es de A docmilio -> Taller
         if (availabilityIsLocal) {
            // CREAR REGISTRO EN ProfessionLocal
            professionLocalRepository.save(
                ProfessionLocal.builder()
                    .professionAvailability(professionAvailabilityFind)
                    .latitude(professionAvailabilityDto.getLatitude())
                    .longitude(professionAvailabilityDto.getLongitude())
                    .state(State.INACTIVE)
                    .build()
            );
         } else {
            // SE ELIMINA EL REGISTRO para poder hacer el cambio de TALLER -> DOMICILIO
            ProfessionLocal professionLocal = professionLocalRepository.findByProfessionAvailabilityId(professionAvailabilityFind.getId());
            if (professionLocal != null) {
               professionLocalRepository.delete(professionLocal);
            }
         }
         // ACTUALIZAMOS LA DISPONIBLIDAD
         professionAvailabilityFind.setAvailability(Availability.builder().id( professionAvailabilityDto.getAvailabilityId() ).build());
      }

      // Si la disponibilidad es TALLER actualizamos su ubicación
      if (availabilityIsLocal){
         ProfessionLocal professionLocalFindAndUpdated = professionLocalRepository.findByProfessionAvailabilityId(professionAvailabilityId);
         professionLocalFindAndUpdated.setLatitude(professionAvailabilityDto.getLatitude());
         professionLocalFindAndUpdated.setLongitude(professionAvailabilityDto.getLongitude());
         professionLocalRepository.save(professionLocalFindAndUpdated);
      }
      //ACTUALIZAMOS PROFESSION AVAILABILITY
      professionAvailabilityFind.setProfession(
          Profession.builder().id( professionAvailabilityDto.getProfessionId() ).build());
      professionAvailabilityFind.setExperience(
          Experience.builder().id( professionAvailabilityDto.getExperienceId() ).build());
      ProfessionAvailability professionAvailabilityUpdated = professionAvailabilityRepository.save(professionAvailabilityFind);

      return new ProfessionAvailabilityDto(
          professionAvailabilityUpdated,
          professionAvailabilityDto.getLatitude(),
          professionAvailabilityDto.getLongitude());
   }

   @Override
   public void delete(Integer professionAvailabilityId) throws ElementNotExistInDBException {
      ProfessionAvailability professionAvailability = professionAvailabilityRepository.findById(professionAvailabilityId)
          .orElseThrow(() -> new ElementNotExistInDBException("ProfessionAvailability con id "+professionAvailabilityId+" no existe"));
      // Si tiene disponibilidad taller ELIMINAR su relación en ProfessionLocal
      if (professionAvailability.getAvailability().getId() == AvailabilityType.EN_TALLER_ID) {
         ProfessionLocal professionLocal = professionLocalRepository.findByProfessionAvailabilityId(professionAvailability.getId());
         professionLocalRepository.delete(professionLocal);
      }

      // Luego eliminar ProfessionAvailability
      professionAvailabilityRepository.delete(professionAvailability);
   }


   private void filterException(Integer technicalId, ProfessionAvailabilityDto professionAvailabilityDto,
                                boolean availabilityIsLocal, boolean containLocation
   ) throws Exception{
      // Antes de validar verifica que no technicalId sea null, luego verifica si existe la ProfessionAvailability
      if (technicalId != null && existsAlReadyProfessionAvailability(technicalId, professionAvailabilityDto))
         throw new DuplicateElementException("Ya existe un registro con la combinación technical, profession, availability y experience ingresados.");

      // CONDICIONES PARA MANEJAR ERRORES
      if (availabilityIsLocal && !containLocation)
         throw new InvalidArgumentException("La disponiblidad ingresada require de una ubicación");
      else if (!availabilityIsLocal && containLocation) {
         throw new InvalidArgumentException("La disponiblidad ingresada no require de longitude y latitude");
      }
   }


   private boolean existsAlReadyProfessionAvailability(Integer technicalId, ProfessionAvailabilityDto professionAvailabilityDto){
      return professionAvailabilityRepository.existsByTechnicalIdAndAvailabilityIdAndProfessionIdAndExperienceId(
          technicalId,
          professionAvailabilityDto.getAvailabilityId(),
          professionAvailabilityDto.getProfessionId(),
          professionAvailabilityDto.getExperienceId()
      );
   }
}
