package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ITypeUserDTO;
import com.springtec.models.payload.MessageResponse;
import com.springtec.models.payload.UserRequest;
import com.springtec.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class UserController {

   private final IUserService userService;

   @GetMapping("user/{id}")
   public ResponseEntity<?> show(@PathVariable Integer id) {
      // todo CREAR UN PAYLOAD
      try {
         ITypeUserDTO user = userService.findById(id);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(user)
                 .build()
             , HttpStatus.OK
         );
      } catch (ElementNotExistInDBException eNEID) {
         return new ResponseEntity<>(
             eNEID.getMessage()
             , HttpStatus.OK
         );
      }

   }

   @PutMapping("user/{id}")
   public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UserRequest request) {
      try {
         userService.update(request, id);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message("Actualizado correctamentee")
                 .build()
             , HttpStatus.OK
         );
      } catch (ElementNotExistInDBException eNEID) {
         return new ResponseEntity<>(
             eNEID.getMessage()
             , HttpStatus.OK
         );
      }
   }

}
