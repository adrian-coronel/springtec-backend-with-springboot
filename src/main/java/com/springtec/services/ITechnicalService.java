package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.Profession;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.User;
import com.springtec.models.payload.TechnicalRequest;

import java.util.List;
import java.util.Map;

public interface ITechnicalService {

    // Cuando se envia el ID en save() CrudRepository reconoce que
    // se quiere actualizar y lo hace por detrás
    Technical save(Technical technical);
    TechnicalDto update(TechnicalRequest technicalRequest, Integer id) throws ElementNotExistInDBException;
    TechnicalDto findById(Integer id) throws ElementNotExistInDBException;
    TechnicalDto findByUser(User user);
    List<TechnicalDto> findByFilters(Map<String, String> filters);
    TechnicalDto delete(Integer technical) throws ElementNotExistInDBException;
    boolean existsByDni(String dni);
    //todo CREAR METODO PARA ACTUALIZAR EL ESTADO DE TRABAJO
    boolean updateWorkingStatus(Integer technicalId, char statusWorking) throws Exception;
    void updateLocation(TechnicalRequest technicalRequest, Integer technicalId) throws ElementNotExistInDBException, IllegalAccessException;
}
