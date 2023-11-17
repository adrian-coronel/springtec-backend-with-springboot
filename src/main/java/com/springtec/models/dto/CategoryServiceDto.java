package com.springtec.models.dto;

import com.springtec.models.entity.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryServiceDto {

    private Integer id;
    private String name;
    private char state;

    public CategoryServiceDto(CategoryService categoryService){
        this.id = categoryService.getId();
        this.name = categoryService.getName();
        this.state =categoryService.getState();

    }
}
