package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.Profession;
import com.springtec.models.payload.MessageResponse;
import com.springtec.models.payload.TechnicalRequest;
import com.springtec.services.ITechnicalService;
import com.springtec.services.impl.TechnicalImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TechnicalController {

    private final TechnicalImplService technicalService;

    @GetMapping("technicals")
    public ResponseEntity<?> showAll(
        @RequestParam Map<String, String> filters
        ){
        List<TechnicalDto> technicalDtos = technicalService.findByFilters(filters);
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

    @PutMapping("technical/{id}")
    public ResponseEntity<?> update(
        @PathVariable Integer id,
        @RequestBody TechnicalRequest technicalRequest
    ){
        try {
            TechnicalDto technicalDto = technicalService.update(technicalRequest, id);
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message("Actualizado correctamente")
                    .body(technicalDto)
                    .build()
                , HttpStatus.OK
            );
        } catch (ElementNotExistInDBException e) {
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message(e.getMessage())
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
