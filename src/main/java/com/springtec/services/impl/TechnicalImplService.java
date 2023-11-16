package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.factories.UserFactory;
import com.springtec.models.dto.ProfessionDto;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.enums.UserType;
import com.springtec.models.payload.TechnicalProfessionRequest;
import com.springtec.models.payload.TechnicalRequest;
import com.springtec.models.repositories.*;
import com.springtec.services.ITechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnicalImplService implements ITechnicalService {

    private final TechnicalRepository technicalRepository;
    private final UserRepository userRepository;
    private final TechnicalProfessionRepository technicalProfessionRepository;
    private final ProfessionRepository professionRepository;
    private final ExperienceRepository experienceRepository;
    private final AvailabilityRepository availabilityRepository;

    @Override
    public Technical save(Technical technical) {
        return technicalRepository.save(technical);
    }

    @Override
    public TechnicalDto update(TechnicalRequest technicalRequest, Integer id) throws ElementNotExistInDBException {
        Technical technicalFind = technicalRepository.findById(id)
            .orElseThrow(() -> new ElementNotExistInDBException("El técnico que intenta actualizar no existe"));

        int i = 0;
        Set<ProfessionDto> professionDtos = new HashSet<>();
        List<TechnicalProfession> techProfList = technicalProfessionRepository.findAllByTechnical(technicalFind);

        for (TechnicalProfession techProf : techProfList) {
            // Obtenemos la profession alcanzada por el usuario
            TechnicalProfessionRequest techProfRequest = technicalRequest.getProfessions().get(i);

            // Actualizamos profesión y experiencia
            Profession profession = professionRepository.findById(techProfRequest.getProfessionId())
                .orElseThrow(() -> new ElementNotExistInDBException("La profesión con id " + techProfRequest.getProfessionId() + " no existe"));
            Experience experience = experienceRepository.findById(techProfRequest.getExperienceId())
                .orElseThrow(() -> new ElementNotExistInDBException("La experiencia con id " + techProfRequest.getExperienceId() + " no existe"));

            techProf.setProfession(profession);
            techProf.setExperience(experience);

            // Agregamos al ProfessionDTO al SET<ProfessionDTO>
            professionDtos.add(
                // Utilizamos la funcion "mapTechnicalProfessionToProfessionDto" para extraer la ProfessionDTO
                mapTechnicalProfessionToProfessionDto( technicalProfessionRepository.save(techProf) )
            );
            i++;
        }

        // ACTUALIZAMOS TODOS LOS CAMPOS
        technicalFind.setName(technicalRequest.getName());
        technicalFind.setLastname(technicalRequest.getLastname());
        technicalFind.setMotherLastname(technicalRequest.getMotherLastname());
        technicalFind.setDni(technicalRequest.getDni());
        technicalFind.setLatitude(technicalRequest.getLatitude());
        technicalFind.setLongitude(technicalRequest.getLongitude());
        technicalFind.setBirthDate(technicalRequest.getBirthDate());

        Availability availability = availabilityRepository.findById(technicalRequest.getAvailabilityId())
            .orElseThrow(() -> new ElementNotExistInDBException("La disponibilidad con id "+technicalRequest.getAvailabilityId()+" no existe"));
        technicalFind.setAvailability(availability);

        Technical technicalUpdate = technicalRepository.save(technicalFind);

        return new TechnicalDto(technicalUpdate, professionDtos);
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

        return new TechnicalDto(technical, professionDtos);
    }

    @Override
    public TechnicalDto findByUser(User user) {
        Technical technical = technicalRepository.findByUser(user);
        List<TechnicalProfession> techProfList = technicalProfessionRepository.findAllByTechnical(technical);
        Set<ProfessionDto> professionDtos = techProfList.stream()
            .map(this::mapTechnicalProfessionToProfessionDto)
            .collect(Collectors.toSet());
        return new TechnicalDto(technical, professionDtos);
    }

    @Override
    public List<TechnicalDto> findByFilters(Map<String, String> filters) {

        List<TechnicalProfession> technicalProfessionList;

        if (filters.containsKey("professionId") && filters.containsKey("availabilityId")) {
            technicalProfessionList = findByProfessionIdAndAvailabilityId(
                Integer.parseInt(filters.get("professionId")),
                Integer.parseInt(filters.get("availabilityId"))
            );
        } else if (filters.containsKey("professionId")) {
            technicalProfessionList = findByProfessionId(Integer.parseInt(filters.get("professionId")));
        } else if (filters.containsKey("availabilityId")) {
            return findByAvailabilityId(Integer.parseInt(filters.get("availabilityId")));
        } else {
            return findAllActiveTechnicalDtos();
        }
        return technicalProfessionList
            .stream()
            .map(techProf -> new TechnicalDto(techProf.getTechnical()))
            .toList();
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

        return new TechnicalDto(technical, professionDtos);
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

    private List<TechnicalProfession> findByProfessionIdAndAvailabilityId(int professionId, int availabilityId) {
        return technicalProfessionRepository
            .findAllByProfessionIdAndTechnicalAvailabilityIdAndTechnicalUserState(
                professionId, availabilityId, State.ACTIVE
            );
    }

    private List<TechnicalProfession> findByProfessionId(int professionId) {
        return technicalProfessionRepository
            .findAllByProfessionIdAndTechnicalUserState(professionId, State.ACTIVE);
    }

    private List<TechnicalDto> findByAvailabilityId(int availabilityId) {
        return technicalRepository.findAllByAvailabilityIdAndUserState(availabilityId, State.ACTIVE)
            .stream()
            .map(technical -> new TechnicalDto(technical, findAllProfessionDtoByTechnical(technical)))
            .toList();
    }

    private List<TechnicalDto> findAllActiveTechnicalDtos() {
        return technicalRepository.findAllByUserState(State.ACTIVE)
            .stream()
            .map(technical -> new TechnicalDto(technical, findAllProfessionDtoByTechnical(technical)))
            .toList();
    }

    private Set<ProfessionDto> findAllProfessionDtoByTechnical(Technical technical) {
        return technicalProfessionRepository.findAllByTechnical(technical).stream()
            .map(this::mapTechnicalProfessionToProfessionDto)
            .collect(Collectors.toSet());
    }
}
