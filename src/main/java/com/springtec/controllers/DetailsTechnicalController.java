package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DetailsTechnicalDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IDetailsTechnicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class DetailsTechnicalController {

   private final IDetailsTechnicalService detailsTechnicalService;

   @GetMapping("technical/{technicalId}/details")
   public ResponseEntity<?> showAll(@PathVariable Integer technicalId){
      try {
         List<DetailsTechnicalDto> detailsTechnicalDtos = detailsTechnicalService.findAll(technicalId);
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


   @PutMapping("technical/{technicalId}/details/{id}")
   public ResponseEntity<?> update(
       @PathVariable Integer technicalId,
       @PathVariable Integer id,
       @RequestBody DetailsTechnicalDto request){
      try {
         DetailsTechnicalDto detailsTechnicalDto = detailsTechnicalService.update(technicalId, id, request);
         return new ResponseEntity<>(
              MessageResponse.builder()
                  .body(detailsTechnicalDto)
                  .build()
             , HttpStatus.OK
         );
      } catch (ElementNotExistInDBException e) {
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(e.getMessage())
                 .build()
             , HttpStatus.INTERNAL_SERVER_ERROR
         );
      }
   }

   @PutMapping("technical/{technicalId}/details")
   public ResponseEntity<?> update(
       @PathVariable Integer technicalId,
       @RequestBody List<DetailsTechnicalDto> request){
      try {
         List<DetailsTechnicalDto> detailsTechnicalDtoList = detailsTechnicalService.updateAll(technicalId, request);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(detailsTechnicalDtoList)
                 .build()
             , HttpStatus.OK
         );
      } catch (ElementNotExistInDBException e) {
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(e.getMessage())
                 .build()
             , HttpStatus.INTERNAL_SERVER_ERROR
         );
      }
   }

}
