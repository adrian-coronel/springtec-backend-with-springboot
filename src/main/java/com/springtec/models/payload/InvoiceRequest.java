package com.springtec.models.payload;

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

   private Integer directRequestId;
   private String task;
   private String description;
   private Double price;
   private Date date;
   private Time hour;

   // Materiales
   List<MaterialRequest> materiales;

}
