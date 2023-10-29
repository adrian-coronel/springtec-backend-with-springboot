package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.Profession;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.TechnicalProfession;
import com.springtec.models.repositories.ProfessionRepository;
import com.springtec.models.repositories.TechnicalProfessionRepository;
import com.springtec.models.repositories.TechnicalRepository;
import com.springtec.services.ITechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TechnicalImplService implements ITechnicalService {

    private final TechnicalRepository technicalRepository;
    private final ProfessionRepository professionRepository;

    @Override
    public List<Technical> findAll() {
        List<Technical> technicals = technicalRepository.findAll();
        technicals.forEach(technical -> {
            Set<Profession> professions = professionRepository.findAllByTechnical(technical);
            technical.setProfessions(professions);
        });
        return technicals;
    }

    @Override
    public Technical save(Technical technical) {
        return technicalRepository.save(technical);
    }

    @Override
    public Technical findById(Integer id) throws ElementNotExistInDBException {
        // todo BUSCAR LA MANERA DE MEJORAR ESTE CODIGO
        Technical technical = technicalRepository.findById(id)
            .orElseThrow(()-> new ElementNotExistInDBException("Tenico no encontrado con ID: "+ id));
        Set<Profession> professions = professionRepository.findAllByTechnical(technical);
        technical.setProfessions(professions);
        return technical;
    }

    @Override
    public void delete(Technical technical) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public boolean existsByDni(String dni) {
        return technicalRepository.existsByDni(dni);
    }


}
