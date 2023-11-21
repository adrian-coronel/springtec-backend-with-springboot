package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DetailsTechnicalDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IDetailsTechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class DetailsTechnicalController {

   private final IDetailsTechnicalService detailsTechnicalService;

   @GetMapping("technical-details/{id}")
   public ResponseEntity<?> showAll(@PathVariable Integer id){
      try {
         List<DetailsTechnicalDto> detailsTechnicalDtos = detailsTechnicalService.findAll(id);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message("")
                 .body(detailsTechnicalDtos)
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


}
