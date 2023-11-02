package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.ITechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TechnicalController {

    private final ITechnicalService technicalService;

    @GetMapping("technicals")
    public ResponseEntity<?> showAll(){
        List<TechnicalDto> technicalDtos = technicalService.findAll();
        if (technicalDtos.isEmpty()) {
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
    public ResponseEntity<?> show(@PathVariable Integer id) {
        try {
            TechnicalDto technicalDto = technicalService.findById(id);
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message("")
                    // Enviamos un tecnico DTO con todos sus datos
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


    @DeleteMapping("technical/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try {
            TechnicalDto technicalDto = technicalService.delete(id);
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message("Eliminado con exito")
                    .body(technicalDto)
                    .build(),
                HttpStatus.OK
            );

        } catch (ElementNotExistInDBException e) {
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message(e.getMessage())
                    .build()
                ,HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
