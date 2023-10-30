package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ProfessionDto;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.Profession;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.TechnicalProfession;
import com.springtec.models.entity.User;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.ProfessionRepository;
import com.springtec.models.repositories.TechnicalProfessionRepository;
import com.springtec.models.repositories.TechnicalRepository;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.ITechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnicalImplService implements ITechnicalService {

    private final TechnicalRepository technicalRepository;
    private final UserRepository userRepository;
    private final TechnicalProfessionRepository technicalProfessionRepository;

    @Override
    public List<TechnicalDto> findAll() {
        // TRAEMOS LOS TECNICOS ACTIVOS
        List<Technical> technicals = technicalRepository.findAllByUserState(State.ACTIVE);
        return technicals.stream()
            .map(technical -> {
                // Obtenemos las profesiones y enperiencias y las mapeamos en PROFESSION DTO
                Set<ProfessionDto> professionDtos = technicalProfessionRepository.findAllByTechnical(technical).stream()
                    .map(this::mapTechnicalProfessionToProfessionDto)
                    .collect(Collectors.toSet());
                return mapTechnicalToDto(technical, professionDtos);
            }).toList();
    }

    @Override
    public Technical save(Technical technical) {
        return technicalRepository.save(technical);
    }

    @Override
    public TechnicalDto findById(Integer id) throws ElementNotExistInDBException {
        if (!technicalRepository.existsByIdAndUserState(id, State.ACTIVE))
            throw new ElementNotExistInDBException("Tenico no encontrado o se encuentra inactivo");

        Technical technical = technicalRepository.findByIdAndUserState(id, State.ACTIVE);

        List<TechnicalProfession> techProfList = technicalProfessionRepository.findAllByTechnical(technical);
        Set<ProfessionDto> professionDtos = techProfList.stream()
            .map(this::mapTechnicalProfessionToProfessionDto)
            .collect(Collectors.toSet());

        return mapTechnicalToDto(technical, professionDtos);
    }


    @Transactional
    @Override
    public TechnicalDto delete(Integer id) throws ElementNotExistInDBException {
        if (!technicalRepository.existsByIdAndUserState(id, State.ACTIVE))
            throw new ElementNotExistInDBException("Tenico no encontrado o ya se encuentra inactivo");

        Technical technical = technicalRepository.findByIdAndUserState(id, State.ACTIVE);
        // CAMBIAMOS EL ESTADO DEL USUARIO
        userRepository.updateStateById(State.INACTIVE, technical.getUser().getId());
        // ALISTAMOS NUESTRO TECHNICAL DTO
        List<TechnicalProfession> techProfList = technicalProfessionRepository.findAllByTechnical(technical);
        Set<ProfessionDto> professionDtos = techProfList.stream()
            .map(this::mapTechnicalProfessionToProfessionDto)
            .collect(Collectors.toSet());

        return mapTechnicalToDto(technical, professionDtos);
    }


    @Override
    public boolean existsByDni(String dni) {
        return technicalRepository.existsByDniAndUserState(dni, State.ACTIVE);
    }


    private ProfessionDto mapTechnicalProfessionToProfessionDto(TechnicalProfession techProf) {
        return ProfessionDto.builder()
            .id(techProf.getProfession().getId())
            .name(techProf.getProfession().getName())
            .experience(techProf.getExperience())
            .build();
    }

    private TechnicalDto mapTechnicalToDto(Technical technical, Set<ProfessionDto> professions) {
        return TechnicalDto.builder()
            .id(technical.getId())
            .name(technical.getName())
            .lastname(technical.getLastname())
            .motherLastname(technical.getMotherLastname())
            .dni(technical.getDni())
            .latitude(technical.getLatitude())
            .longitude(technical.getLongitude())
            .birthDate(technical.getBirthDate())
            .user(technical.getUser())
            .professions(professions)
            .availability(technical.getAvailability())
            .build();
    }


}
