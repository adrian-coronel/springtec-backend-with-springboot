package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.payload.DirectRequestRequest;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IDirectRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class DirectRequestController {

   private final IDirectRequestService directRequestService;


   @GetMapping(value="directrequest/{id}")
   public ResponseEntity<?> show(@PathVariable Integer id){
      try {
         DirectRequestDto directRequestDto = directRequestService.findById(id);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(directRequestDto)
                 .build()
             , HttpStatus.OK
         );
      } catch (Exception e) {
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(e.getMessage())
                 .build()
             , HttpStatus.INTERNAL_SERVER_ERROR
         );
      }
   }

   @PostMapping(value = "directrequest",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<?> save(
       @ModelAttribute DirectRequestRequest directRequestRequest
   ) {
      try {
         System.out.println(directRequestRequest);
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
             , HttpStatus.METHOD_NOT_ALLOWED
         );
      }
   }




}
