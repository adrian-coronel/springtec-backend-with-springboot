package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.User;
import com.springtec.models.payload.TechnicalRequest;

import java.util.List;

public interface ITechnicalService {

    List<TechnicalDto> findAll();
    // Cuando se envia el ID en save() CrudRepository reconoce que
    // se quiere actualizar y lo hace por detrás
    Technical save(Technical technical);
    TechnicalDto update(TechnicalRequest technicalRequest, Integer id) throws ElementNotExistInDBException;
    TechnicalDto findById(Integer id) throws ElementNotExistInDBException;
    TechnicalDto findByUser(User user);
    TechnicalDto delete(Integer technical) throws ElementNotExistInDBException;
    boolean existsByDni(String dni);

}
