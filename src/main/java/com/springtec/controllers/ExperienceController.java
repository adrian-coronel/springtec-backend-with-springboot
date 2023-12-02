package com.springtec.controllers;

import com.springtec.models.dto.ExperienceDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IExperiencieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ExperienceController {

    private final IExperiencieService experiencieService;
    @GetMapping("experiences")
    public ResponseEntity<?> showAll() {
        List<ExperienceDto> experiencesList = experiencieService.getAll();

        if(experiencesList.isEmpty()){
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("No existe ningun registro")
                            .build()
                    , HttpStatus.NOT_FOUND
        );
        }

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .body(experiencesList)
                        .build()
                , HttpStatus.OK
        );
    }
}
