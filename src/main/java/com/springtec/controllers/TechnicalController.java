package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.Profession;
import com.springtec.models.payload.LocationRequest;
import com.springtec.models.payload.MessageResponse;
import com.springtec.models.payload.StateRequest;
import com.springtec.models.payload.TechnicalRequest;
import com.springtec.services.ITechnicalService;
import com.springtec.services.impl.TechnicalImplService;
import jakarta.validation.Valid;
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

    @CrossOrigin(origins = {"http://localhost:5173/"})

    @GetMapping("technicals")
    public ResponseEntity<?> showAll(
        @RequestParam Map<String, String> filters
        ){
        System.out.println(filters);
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
        @Valid @RequestBody TechnicalRequest technicalRequest
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
                , HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    @PutMapping("technical/{id}/update-workingstatus")
    public ResponseEntity<?> updateWorkingStatus(
        @PathVariable Integer id,
        @Valid @RequestBody StateRequest stateRequest
        ){
        try {
            boolean isStatusWorkingActive = technicalService.updateWorkingStatus(id, stateRequest.getStateId().toString().charAt(0));
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message("Actualizado correctamente")
                    .body(isStatusWorkingActive)
                    .build()
                , HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message(e.getMessage())
                    .build()
                , HttpStatus.NOT_FOUND
            );
        }
    }


    @PutMapping("technical/{id}/update-location")
    public ResponseEntity<?> updateLocation(
        @PathVariable Integer id,
        @Valid @RequestBody LocationRequest locationRequest
    ){
        try {
            technicalService.updateLocation(locationRequest, id);
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message("Actualizado correctamente")
                    .build()
                , HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message(e.getMessage())
                    .build()
                , HttpStatus.NOT_FOUND
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
