package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ServiceTypeAvailabilityDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.impl.IServiceTypeAvailabilityService;
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
public class ServiceTypeAvailabilityController {

   private final IServiceTypeAvailabilityService serviceTypeAvailabilityService;

   @GetMapping("services-availability")
   public ResponseEntity<?> showAllByFilters(@RequestParam Map<String, String> filters){
      try {
         List<ServiceTypeAvailabilityDto> serviceTypeAvailabilityDtoList = serviceTypeAvailabilityService.findByFilters(filters);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(serviceTypeAvailabilityDtoList)
                 .build()
             , HttpStatus.OK
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
