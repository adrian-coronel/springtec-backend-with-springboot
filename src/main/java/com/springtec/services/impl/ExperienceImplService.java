package com.springtec.services.impl;

import com.springtec.models.dto.ExperienceDto;
import com.springtec.models.entity.Experience;
import com.springtec.models.repositories.ExperienceRepository;
import com.springtec.services.IExperiencieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExperienceImplService implements IExperiencieService {

    private final ExperienceRepository experienceRepository;

    @Override
    public List<ExperienceDto> getAll() {
        return experienceRepository.findAll()
                    .stream()
                    .map(ExperienceDto::new)
                    .toList();


    }

}
