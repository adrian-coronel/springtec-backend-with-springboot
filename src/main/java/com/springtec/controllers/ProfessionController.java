package com.springtec.controllers;

import com.springtec.models.dto.ProfessionDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IProfessionService;
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
public class ProfessionController {

   private final IProfessionService professionService;

   @GetMapping("professions")
   public ResponseEntity<?> showAll() {
      List<ProfessionDto> professionDtoList = professionService.findAll();
      return new ResponseEntity<>(
          MessageResponse.builder()
              .message(null)
              .body(professionDtoList)
              .build()
          , HttpStatus.OK
      );
   }


}
