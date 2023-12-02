package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;

import com.springtec.models.dto.ProfessionAvailabilityDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IProfessionAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ProfessionAvailabilityController {

   private final IProfessionAvailabilityService professionAvailabilityService;


   @GetMapping("technicals/{technicalId}/professions-availability")
   public ResponseEntity<?> showAllbyTechnical(@PathVariable Integer technicalId){
      try {
         Set<ProfessionAvailabilityDto> professionAvailabilityDtoSet = professionAvailabilityService.findAllByTechnical(technicalId);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(professionAvailabilityDtoSet)
                 .build()
             , HttpStatus.OK
         );
      } catch (ElementNotExistInDBException exist){
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(exist.getMessage())
                 .build()
             , HttpStatus.NOT_FOUND
         );
      }

   }



   @GetMapping("technical/professions-availability/{TechnicalId}/{ProfessionId}")
   public ResponseEntity<?> showAllProfessionsAvailabilityByProfession (
           @PathVariable Integer TechnicalId,@PathVariable Integer ProfessionId
   ){
      Set<ProfessionAvailabilityDto> professionAvailabilityDtos = professionAvailabilityService.findAllByTechnicalAndProfessionId(TechnicalId,ProfessionId);
      return new ResponseEntity<>(
              MessageResponse.builder()
                      .body(professionAvailabilityDtos)
                      .build()
              , HttpStatus.OK
      );
   }



   @GetMapping("technicals/professions-availability/{professionAvailabilityId}")
   public ResponseEntity<?> show(@PathVariable Integer professionAvailabilityId){
      try {
         ProfessionAvailabilityDto professionAvailabilityDto = professionAvailabilityService.findById(professionAvailabilityId);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(professionAvailabilityDto)
                 .build()
             , HttpStatus.OK
         );
      } catch (ElementNotExistInDBException exist){
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(exist.getMessage())
                 .build()
             , HttpStatus.NOT_FOUND
         );
      }
   }


   @PostMapping("technicals/{technicalId}/professions-availability")
   public ResponseEntity<?> save(
       @PathVariable("technicalId") Integer technicalId,
       @RequestBody ProfessionAvailabilityDto professionAvailabilityDto
   ){
      try {
         ProfessionAvailabilityDto professionAvailabilitySaved = professionAvailabilityService.save(technicalId, professionAvailabilityDto);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(professionAvailabilitySaved)
                 .build()
             , HttpStatus.CREATED
         );
      } catch (Exception eNEID) {
         return new ResponseEntity<>(
             eNEID.getMessage()
             , HttpStatus.METHOD_NOT_ALLOWED
         );
      }
   }

   @PutMapping("technical/professions-availability/{professionAvailabilityId}")
   public ResponseEntity<?> update(
       @PathVariable Integer professionAvailabilityId,
       @RequestBody ProfessionAvailabilityDto professionAvailabilityDto
   ){
      try {
         ProfessionAvailabilityDto professionAvailabilityDtoUpdated = professionAvailabilityService
             .update(professionAvailabilityId, professionAvailabilityDto);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message("Actualizado correctamente")
                 .body(professionAvailabilityDtoUpdated)
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

   @DeleteMapping("technical/professions-availability/{professionAvailabilityId}")
   public ResponseEntity<?> delete(@PathVariable Integer professionAvailabilityId){
      try {
         professionAvailabilityService.delete(professionAvailabilityId);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message("Eliminado correctamente")
                 .body(true)
                 .build()
             , HttpStatus.OK
         );
      } catch (Exception e) {
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(e.getMessage())
                 .body(false)
                 .build()
             , HttpStatus.NOT_FOUND
         );
      }
   }

}
