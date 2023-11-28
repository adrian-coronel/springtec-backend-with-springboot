package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.Services;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

   private Integer id;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer categoryServiceId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer currencyTypeId;
   private String name;
   private String description;
   private double price;
   public ServiceDto(Services service) {
      this.id = service.getId();
      this.categoryServiceId = service.getCategoryService().getId();
      this.currencyTypeId = service.getCurrencyType().getId();
      this.name = service.getName();
      this.description = service.getDescription();
      this.price = service.getPrice();
   }
}
