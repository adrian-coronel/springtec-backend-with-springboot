package com.springtec.controllers;

import com.springtec.models.dto.ServiceDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
