package com.springtec.services;

import com.springtec.models.dto.CategoryServiceDto;

import java.util.List;

public interface ICategoryService {
    List<CategoryServiceDto> getAll();
}
