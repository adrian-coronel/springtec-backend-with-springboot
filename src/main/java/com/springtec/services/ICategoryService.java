package com.springtec.services;

import com.springtec.models.dto.CategoryServiceDTO;
import com.springtec.models.dto.ProfessionDto;

import java.util.List;

public interface ICategoryService {
    List<CategoryServiceDTO> getAll();
}
