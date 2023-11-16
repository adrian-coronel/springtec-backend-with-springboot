package com.springtec.controllers;

import com.springtec.models.dto.AvailabilityDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class AvailabilityController {

   private final IAvailabilityService availabilityService;

   @GetMapping("availabilities")
   public ResponseEntity<?> showAll() {
      List<AvailabilityDto> availabilityDtos = availabilityService.findAll();
      return new ResponseEntity<>(
          MessageResponse.builder()
              .message(null)
              .body(availabilityDtos)
              .build()
          , HttpStatus.OK
      );
   }

}
