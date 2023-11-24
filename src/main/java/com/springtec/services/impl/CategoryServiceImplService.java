package com.springtec.services.impl;

import com.springtec.models.dto.CategoryServiceDto;
import com.springtec.models.repositories.CategoryServiceRepository;
import com.springtec.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplService implements ICategoryService {

    private final CategoryServiceRepository categoryService;

    @Override
    public List<CategoryServiceDto> getAll() {

        return categoryService.findAll()
                .stream()
                .map(CategoryServiceDto::new)
                .toList();
    }



}
