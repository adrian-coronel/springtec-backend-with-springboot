package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ReviewDto;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ReviewController {

   private final IReviewService reviewService;

   @GetMapping("reviews")
   public ResponseEntity<?> showAll(@RequestParam Map<String,String> filters){
      try {
         List<ReviewDto> reviewDtoList = reviewService.findAllByFilters(filters);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(reviewDtoList)
                 .build()
             , HttpStatus.OK
         );
      } catch (ElementNotExistInDBException e) {
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(e.getMessage())
                 .build()
             , HttpStatus.BAD_REQUEST
         );
      }
   }

   @GetMapping("reviews/{id}")
   public ResponseEntity<?> show(@PathVariable Integer id){
      try {
         ReviewDto reviewDto = reviewService.findById(id);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(reviewDto)
                 .build()
             , HttpStatus.OK
         );
      } catch (ElementNotExistInDBException e) {
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(e.getMessage())
                 .build()
             , HttpStatus.BAD_REQUEST
         );
      }
   }

   @PostMapping("reviews")
   public ResponseEntity<?> save(@Valid @RequestBody ReviewDto review ){
      try {
         ReviewDto reviewDto = reviewService.save(review);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(reviewDto)
                 .build()
             ,HttpStatus.CREATED
         );
      } catch (ElementNotExistInDBException e) {
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(e.getMessage())
                 .build()
             ,HttpStatus.BAD_REQUEST
         );
      }
   }



   /**
    *  Spring Boot llamará a este método cuando el objeto Usuario especificado no sea válido .
    * @param ex
    * @return
    */
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(MethodArgumentNotValidException.class) // Especificamos como se va a manejar esta excepcion
   public Map<String, String> handleValidationExceptions(
       MethodArgumentNotValidException ex) {
      Map<String, String> errors = new HashMap<>();
      ex.getBindingResult().getAllErrors().forEach((error) -> {
         // Obtenemos el nombre y mensaje para enviarla
         String fieldName = ((FieldError) error).getField();
         String errorMessage = error.getDefaultMessage();
         errors.put(fieldName, errorMessage);
      });
      return errors;
   }

}
