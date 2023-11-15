package com.springtec.controllers;


import com.springtec.models.entity.Profession;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IProfessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ProfesssionController {
    private final IProfessionService ProfessionService;

    @GetMapping("professions")
    public ResponseEntity<?> getAllProfesssions(){
        List<Profession> professions= ProfessionService.findAll();

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .body(professions)
                        .build()
                , HttpStatus.OK
        );
    }

}
