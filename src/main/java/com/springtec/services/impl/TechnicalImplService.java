package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;

import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.*;
import com.springtec.models.enums.AvailabilityType;
import com.springtec.models.enums.State;
import com.springtec.models.payload.LocationRequest;
import com.springtec.models.payload.TechnicalRequest;
import com.springtec.models.repositories.*;
import com.springtec.services.IProfessionAvailabilityService;
import com.springtec.services.ITechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TechnicalImplService implements ITechnicalService {

    private final TechnicalRepository technicalRepository;
    private final IProfessionAvailabilityService professionAvailabilityService;
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
            return getAllTehnicalsAndProfessionAvailabilityByAvailabilityId(
                filters,
                Integer.parseInt(filters.get("availabilityId"))
            );

        }
        if (filters.containsKey("latitude") && filters.containsKey("longitude") && filters.containsKey("distance")
            && filters.containsKey("professionId"))
        {
           List<TechnicalDto> technicalsHaveTaller = getAllTehnicalsAndProfessionAvailabilityByAvailabilityId(
               filters,
               AvailabilityType.EN_TALLER_ID
           );
           List<TechnicalDto> technicalsNoHaveTaller = getAllTehnicalsAndProfessionAvailabilityByAvailabilityId(
               filters,
               AvailabilityType.A_DOMICILIO_ID
           );

           List<TechnicalDto> listaUnida = new ArrayList<>(technicalsHaveTaller);
           listaUnida.addAll(technicalsNoHaveTaller);

           return listaUnida;

        }


        return null;
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

    @Override
    public boolean updateWorkingStatus(Integer technicalId, char statusWorking) throws Exception {
        //todo Cambiar, el endpoint ya solito cambia el estado

        // Si no se envia un valor para activar o desactivar
        if(!(statusWorking == State.ACTIVE || statusWorking == State.INACTIVE))
            throw new IllegalArgumentException("El estado solo de trabajo solo puede contener 0 o 1");
        if (!technicalRepository.existsByIdAndUserState(technicalId, State.ACTIVE))
            throw new ElementNotExistInDBException("Tenico no encontrado o ya se encuentra inactivo");

        Technical technical = technicalRepository.findByIdAndUserState(technicalId, State.ACTIVE);
        technical.setWorkingStatus(statusWorking);
        technicalRepository.save(technical);
        return statusWorking == State.ACTIVE;
    }


    @Override
    public void updateLocation(LocationRequest locationRequest, Integer technicalId) throws ElementNotExistInDBException {
        String isUpdate = technicalRepository.updateTechnicalLocation(technicalId, locationRequest.getLatitude(), locationRequest.getLongitude(), State.ACTIVE);
        if (isUpdate.contains("0"))
            throw new ElementNotExistInDBException("Técnico con id " + technicalId + " no existe o su estado de trabajo no es activo.");
    }


    private List<TechnicalDto> getAllTehnicalsAndProfessionAvailabilityByAvailabilityId(Map<String, String> filters, Integer availabilityId){
        if(availabilityId == AvailabilityType.EN_TALLER_ID) {
            return technicalListToTechnicalDtoList(
                technicalRepository.filterTechnicalsAvailabilityIsLocal(
                    availabilityId,
                    Integer.parseInt(filters.get("professionId")),
                    Integer.parseInt(filters.get("distance")),
                    Double.parseDouble(filters.get("latitude")),
                    Double.parseDouble(filters.get("longitude")),
                    State.ACTIVE
                )
                , availabilityId
                , Integer.parseInt(filters.get("professionId"))
            );
        }
        return technicalListToTechnicalDtoList(
            technicalRepository.filterTechnicalsAvailabilityNoInLocal(
                availabilityId,
                Integer.parseInt(filters.get("professionId")),
                Integer.parseInt(filters.get("distance")),
                Double.parseDouble(filters.get("latitude")),
                Double.parseDouble(filters.get("longitude")),
                State.ACTIVE
            )
            , availabilityId
            , Integer.parseInt(filters.get("professionId"))
        );
    }

    private List<TechnicalDto> technicalListToTechnicalDtoList(List<Technical> technicals,Integer availabilityId, Integer professionId){
        return technicals
            .stream()
            .map(technical ->
                // Mapeamos al tecnico con la ProfessionAvailability y su professionAvailability
                new TechnicalDto(
                    technical,
                    professionAvailabilityService.findByTechnicalIdAndAvailabilityIdAndProfessionId(
                        technical.getId(), availabilityId, professionId)
                ))
            .toList();
    }


}
