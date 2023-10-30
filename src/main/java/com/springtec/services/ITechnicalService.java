package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.Technical;

import java.util.List;

public interface ITechnicalService {

    List<TechnicalDto> findAll();
    // Cuando se envia el ID en save() CrudRepository reconoce que
    // se quiere actualizar y lo hace por detrás
    Technical save(Technical technical);
    TechnicalDto findById(Integer id) throws ElementNotExistInDBException;
    TechnicalDto delete(Integer technical) throws ElementNotExistInDBException;
    boolean existsByDni(String dni);
}
