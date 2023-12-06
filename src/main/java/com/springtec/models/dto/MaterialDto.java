package com.springtec.models.dto;

import com.springtec.models.entity.Material;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDto {

   private Integer id;
   private String name;
   private Double price;
   private Integer stock;

   public MaterialDto(Material material){
      this.id = material.getId();
      this.name = material.getName();
      this.price = material.getPrice();
      this.stock = material.getStock();
   }

}
