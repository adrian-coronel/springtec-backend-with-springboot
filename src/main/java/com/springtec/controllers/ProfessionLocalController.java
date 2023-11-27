package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ProfessionLocalDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.impl.ProfessionLocalImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ProfessionLocalController {

   private final ProfessionLocalImplService professionLocalImplService;

   @PutMapping("professions-availability/{professionAvailabilityId}/update-localstate")
   public ResponseEntity<?> updateLocalState(
       @PathVariable Integer professionAvailabilityId,
       @RequestBody ProfessionLocalDto professionLocalDto){
      try {
         boolean isActive = professionLocalImplService.updateState(professionAvailabilityId, professionLocalDto.getState());
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message("Actualizado correctamente")
                 .body(isActive)
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

}
