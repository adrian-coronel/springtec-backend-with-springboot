package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.payload.DirectRequestRequest;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IDirectRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class DirectRequestController {

   private final IDirectRequestService directRequestService;

   @PostMapping("directrequest")
   public ResponseEntity<?> save(
       @RequestBody DirectRequestRequest directRequestRequest
   ){
      try {
         DirectRequestDto directRequestDto = directRequestService.save(directRequestRequest);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message("Guardado correctamente")
                 .body(directRequestDto)
                 .build()
             , HttpStatus.CREATED
         );
      } catch (ElementNotExistInDBException e) {
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(e.getMessage())
                 .build()
             , HttpStatus.CREATED
         );
      }
   }

}
