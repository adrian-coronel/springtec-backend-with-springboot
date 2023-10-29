package com.springtec.services;

import com.springtec.models.entity.Profession;

import java.util.List;

public interface IProfessionService {

    List<Profession> findAll();
    Profession findById(Integer id);
    Profession save(Profession profession);
    boolean existsById(Integer id);

}
