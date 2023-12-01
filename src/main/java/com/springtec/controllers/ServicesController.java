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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ServicesController {

   private final IServicesService servicesService;

   @GetMapping("services")
   public ResponseEntity<?> showAll(
       @RequestParam Map<String, String> filters
   ){
      List<ServiceDto> serviceDtos =servicesService.findByFilters(filters);
      return new ResponseEntity<>(
          MessageResponse.builder()
              .message("")
              .body(serviceDtos)
              .build()
          , HttpStatus.OK
      );
   }

   @GetMapping("services/{id}")
   public ResponseEntity<?> show(
       @PathVariable Integer id
   ){
      try {
         ServiceDto serviceDto = servicesService.findById(id);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message("")
                 .body(serviceDto)
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
