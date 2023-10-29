package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.entity.Technical;

import java.util.List;

public interface ITechnicalService {

    List<Technical> findAll();
    // Cuando se envia el ID en save() CrudRepository reconoce que
    // se quiere actualizar y lo hace por detrás
    Technical save(Technical technical);
    Technical findById(Integer id) throws ElementNotExistInDBException;
    void delete(Technical technical);
    boolean existsById(Integer id);
    boolean existsByDni(String dni);
}
