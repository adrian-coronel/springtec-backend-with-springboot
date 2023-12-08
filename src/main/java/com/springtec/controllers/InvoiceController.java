package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.InvoiceDto;
import com.springtec.models.payload.InvoiceRequest;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
   public ResponseEntity<?> save(@RequestBody InvoiceRequest invoiceRequest){
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

}
