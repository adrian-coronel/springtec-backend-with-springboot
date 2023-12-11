package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.InvoiceDto;
import com.springtec.models.payload.InvoiceRequest;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IInvoiceService;
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
public class InvoiceController {

   private final IInvoiceService invoiceService;

   @GetMapping("invoices")
   public ResponseEntity<?> showByFilters(@RequestParam Map<String, String> filters){
      try {
         List<InvoiceDto> invoices = invoiceService.findByFilters(filters);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(invoices)
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

   @PostMapping("invoice")
   public ResponseEntity<?> save(@Valid @RequestBody InvoiceRequest invoiceRequest){
      try {
         InvoiceDto invoiceDto = invoiceService.save(invoiceRequest);
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .body(invoiceDto)
                 .build()
             , HttpStatus.CREATED
         );
      } catch (Exception e) {
         return new ResponseEntity<>(
             MessageResponse.builder()
                 .message(e.getMessage())
                 .build()
             , HttpStatus.BAD_REQUEST
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
