package com.springtec.services.impl;

import com.springtec.models.entity.Technical;
import com.springtec.models.repositories.TechnicalRepository;
import com.springtec.services.ITechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnicalImplService implements ITechnicalService {

    private final TechnicalRepository technicalRepository;

    @Override
    public List<Technical> findAll() {
        return null;
    }

    @Override
    public Technical save(Technical technical) {
        return technicalRepository.save(technical);
    }

    @Override
    public Technical findById(Integer id) {
        return null;
    }

    @Override
    public void delete(Technical technical) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }
}
