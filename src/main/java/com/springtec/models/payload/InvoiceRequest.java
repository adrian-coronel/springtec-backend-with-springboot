package com.springtec.models.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {

   @NotNull(message = "directRequestId no puede ser nulo")
   private Integer directRequestId;
   @NotBlank(message = "task es obligatorio")
   private String task;
   @NotBlank(message = "description es obligatorio")
   private String description;
   @NotNull(message = "price no puede ser nulo")
   private Double price;
   private Date date;
   private Time hour;

   // Materiales
   List<MaterialRequest> materiales;

}
