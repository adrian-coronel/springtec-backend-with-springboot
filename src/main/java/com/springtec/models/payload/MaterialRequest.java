package com.springtec.models.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequest {

   private String name;
   private Double price;
   private Integer stock;

}
