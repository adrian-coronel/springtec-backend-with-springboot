package com.springtec.services.impl;

import com.springtec.models.entity.Profession;
import com.springtec.models.repositories.ProfessionRepository;
import com.springtec.services.IProfessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionImplService implements IProfessionService {

    private final ProfessionRepository professionRepository;

    @Override
    public List<Profession> findAll() {
        return professionRepository.findAll();
    }

    @Override
    public Profession findById(Integer id) {
        return professionRepository.findById(id).orElseThrow();
    }

    @Override
    public Profession save(Profession profession) {

        return professionRepository.save(profession);
    }

    public Boolean existsAllById(List<Integer> Ids) {
        boolean existsAll = true;
        for (Integer id : Ids) {
            boolean exist = professionRepository.existsById(id);
            if (!exist) existsAll = false;
        }
        return existsAll;
    }
}
