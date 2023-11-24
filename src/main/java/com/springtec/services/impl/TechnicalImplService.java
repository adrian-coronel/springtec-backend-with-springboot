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
            && filters.containsKey("professionId") && filters.containsKey("availabilityId"))
        {
            return null;
        }
        if (filters.containsKey("latitude") && filters.containsKey("longitude") && filters.containsKey("distance")
            && filters.containsKey("professionId"))
        {
            return null;
        }
        if (filters.containsKey("professionId") && filters.containsKey("excludeAvailabilityId")) {
            return null;
        }
        if (filters.containsKey("professionId")) {
            return null;
        }
        if (filters.containsKey("excludeAvailabilityId")) {
            return null;
        }

        return findAllActiveTechnicalDtos();
    }

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

    @Transactional
    @Override
    public TechnicalDto delete(Integer id) throws ElementNotExistInDBException {
        if (!technicalRepository.existsByIdAndUserState(id, State.ACTIVE))
            throw new ElementNotExistInDBException("Tenico no encontrado o ya se encuentra inactivo");

        Technical technical = technicalRepository.findByIdAndUserState(id, State.ACTIVE);
        // CAMBIAMOS EL ESTADO DEL USUARIO
        userRepository.updateStateById(State.INACTIVE, technical.getUser().getId());
        // INABILITAMOS TODOS LOS DETALLES DEL TECHNICAL
        //todo DESHABILITAR LAS PROFESIONES DISPNIBLES

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
