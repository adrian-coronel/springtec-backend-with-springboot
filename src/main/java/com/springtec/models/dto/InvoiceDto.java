package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.Invoice;
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
public class InvoiceDto {

   private Integer id;
   private String task;
   private String description;
   private Double price;
   private Date date;
   private Time hour;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer directRequestId;
   private List<MaterialDto> materiales;

}
