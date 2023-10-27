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
        if (technicals.isEmpty()) {
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message("No hay registros.")
                    .body(null)
                    .build()
                , HttpStatus.OK
            );
        }
        // ModelMapper simplifica la conversión de objetos de un tipo a otro.
        ModelMapper modelMapper = new ModelMapper();

        // Mapear la lista de entidades a una lista de DTOs
        List<TechnicalDto> technicalDtos = technicals.stream()
            .map(technical -> modelMapper.map(technical, TechnicalDto.class))
            .toList();
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
            ModelMapper modelMapper = new ModelMapper();
            TechnicalDto technicalDto = modelMapper.map(technical, TechnicalDto.class);

            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message("")
                    .body(technicalDto)
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
