package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ServiceDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.models.payload.ServiceRequest;
import com.springtec.services.IServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ServiceController {

   private final IServicesService servicesService;

   @PostMapping(value = "services",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   private ResponseEntity<?> save(
       @ModelAttribute ServiceRequest serviceRequest
   ){
      try {
         System.out.println(serviceRequest);
         ServiceDto serviceDto = servicesService.save(serviceRequest);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message("Guardado correctamente")
                 .body(serviceDto)
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
