package com.springtec.controllers;


import com.springtec.models.dto.CategoryServiceDTO;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class CategoryServiceController {


    private final ICategoryService categoryService;
    @GetMapping("categorieService")
    public ResponseEntity<?> findAll(){
        List<CategoryServiceDTO> categorias= categoryService.getAll();
        if(categorias.isEmpty()) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("No hay Categorias.")
                            .body(null)
                            .build()
                    , HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .body(categorias)
                        .build()
                , HttpStatus.OK
        );
    }
}
