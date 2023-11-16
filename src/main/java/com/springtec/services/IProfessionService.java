package com.springtec.services;

import com.springtec.models.dto.ProfessionDto;
import com.springtec.models.entity.Profession;

import java.util.List;

public interface IProfessionService {

    List<ProfessionDto> findAll();
    Profession findById(Integer id);
    Profession save(Profession profession);
    boolean existsById(Integer id);

}
