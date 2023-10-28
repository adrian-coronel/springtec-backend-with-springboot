package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.TechnicalProfession;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.ITechnicalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class TechnicalController {

    private final ITechnicalService technicalService;

    @GetMapping("technicals")
    public ResponseEntity<?> showAll(){
        List<Technical> technicals = technicalService.findAll();
        // Mapeamos los tecnicos a tecnicosDTO con todos sus campos
        List<TechnicalDto> technicalDtos = technicals.stream()
            .map(TechnicalDto::new).toList();

        if (technicals.isEmpty()) {
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message("No hay registros.")
                    .body(null)
                    .build()
                , HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
            MessageResponse.builder()
                .message("")
                .body(technicalDtos)
                .build()
            , HttpStatus.OK
        );
    }

    @GetMapping("technical/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        try {
            Technical technical = technicalService.findById(id);
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message("")
                    // Enviamos un tecnico DTO con todos sus datos
                    .body(new TechnicalDto(technical))
                    .build()
                , HttpStatus.OK
            );

        } catch (ElementNotExistInDBException existInDBException) {
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message(existInDBException.getMessage())
                    .body(null)
                    .build()
                , HttpStatus.OK
            );
        }
    }

}
