package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;

import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.payload.TechnicalRequest;
import com.springtec.models.repositories.*;
import com.springtec.services.ITechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TechnicalImplService implements ITechnicalService {

    private final TechnicalRepository technicalRepository;
    private final UserRepository userRepository;
    private final DetailsTechnicalRepository detailsTechnicalRepository;


    @Override
    public Technical save(Technical technical) {
        return technicalRepository.save(technical);
    }


    @Override
    public TechnicalDto update(TechnicalRequest technicalRequest, Integer id) throws ElementNotExistInDBException {
        Technical technicalFind = technicalRepository.findById(id)
            .orElseThrow(() -> new ElementNotExistInDBException("El técnico que intenta actualizar no existe"));

        // ACTUALIZAMOS TODOS LOS CAMPOS
        technicalFind.setName(technicalRequest.getName());
        technicalFind.setLastname(technicalRequest.getLastname());
        technicalFind.setMotherLastname(technicalRequest.getMotherLastname());
        technicalFind.setBirthDate(technicalRequest.getBirthDate());

        Technical technicalUpdate = technicalRepository.save(technicalFind);

        return new TechnicalDto(technicalUpdate);
    }

    @Override
    public TechnicalDto findById(Integer id) throws ElementNotExistInDBException {
        if (!technicalRepository.existsByIdAndUserState(id, State.ACTIVE))
            throw new ElementNotExistInDBException("Tenico no encontrado o se encuentra inactivo");
        Technical technical = technicalRepository.findByIdAndUserState(id, State.ACTIVE);
        return new TechnicalDto(technical);
    }

    @Override
    public TechnicalDto findByUser(User user) {
        Technical technical = technicalRepository.findByUser(user);
        return new TechnicalDto(technical);
    }

    @Transactional
    @Override
    public List<TechnicalDto> findByFilters(Map<String, String> filters) {

        if (filters.containsKey("latitude") && filters.containsKey("longitude") && filters.containsKey("distance")
            && filters.containsKey("professionId") && filters.containsKey("excludeAvailabilityId"))
        {
            List<Technical> technicals = technicalRepository.findAllNeabyByProfessionIdAndAvailabilityId(
                Double.parseDouble(filters.get("latitude")),
                Double.parseDouble(filters.get("longitude")),
                Double.parseDouble(filters.get("distance")),
                Integer.parseInt(filters.get("professionId")),
                Integer.parseInt(filters.get("excludeAvailabilityId"))
            );
            return technicalListToTechnicalDtoList(
              technicals
            );
        }
        if (filters.containsKey("professionId") && filters.containsKey("excludeAvailabilityId")) {
            return technicalListToTechnicalDtoList(
                technicalRepository.findByProfessionAndExcludeAvailability(
                    Integer.parseInt(filters.get("professionId")),
                    Integer.parseInt(filters.get("excludeAvailabilityId"))
                )
            );
        }
        if (filters.containsKey("professionId")) {
            return technicalListToTechnicalDtoList(
                technicalRepository.findAllByDetailsTechnicalsProfessionId(
                    Integer.parseInt(filters.get("professionId"))
                )
            );
        }
        if (filters.containsKey("excludeAvailabilityId")) {
            return technicalListToTechnicalDtoList(
                technicalRepository.findAllByDetailsTechnicalsExcludeAvailabilityId(
                    Integer.parseInt(filters.get("excludeAvailabilityId"))
                )
            );
        }

        return findAllActiveTechnicalDtos();
    }



    @Transactional
    @Override
    public TechnicalDto delete(Integer id) throws ElementNotExistInDBException {
        if (!technicalRepository.existsByIdAndUserState(id, State.ACTIVE))
            throw new ElementNotExistInDBException("Tenico no encontrado o ya se encuentra inactivo");

        Technical technical = technicalRepository.findByIdAndUserState(id, State.ACTIVE);
        // CAMBIAMOS EL ESTADO DEL USUARIO
        userRepository.updateStateById(State.INACTIVE, technical.getUser().getId());
        // INABILITAMOS TODOS LOS DETALLES DEL TECHNICAL
        detailsTechnicalRepository.updateDetailsTechnicalState(State.INACTIVE, technical.getId());

        return new TechnicalDto(technical);
    }


    @Override
    public boolean existsByDni(String dni) {
        return technicalRepository.existsByDniAndUserState(dni, State.ACTIVE);
    }

    private List<TechnicalDto> findAllActiveTechnicalDtos() {
        return technicalListToTechnicalDtoList(
            technicalRepository.findAllByUserState(State.ACTIVE)
        );
    }

    private List<TechnicalDto> technicalListToTechnicalDtoList(List<Technical> technicals){
        return technicals
            .stream()
            .map(TechnicalDto::new)
            .toList();
    }


}
