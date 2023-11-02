package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class UserController {

   private final IUserService userService;

   @GetMapping("user/{id}")
   public ResponseEntity<?> show(@PathVariable Integer id) {
      // todo CREAR UN PAYLOAD

      try {
         return new ResponseEntity<>(
             userService.findById(id)
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
