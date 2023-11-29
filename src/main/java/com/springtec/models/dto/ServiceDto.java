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
   private CategoryServiceDto categoryService;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer currencyTypeId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private CurrencyTypeDto currencyType;
   private String name;
   private String description;
   private double price;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private ImageUploadDto file;

   public ServiceDto(Services service) {
      this.id = service.getId();
      this.categoryServiceId = service.getCategoryService().getId();
      this.currencyTypeId = service.getCurrencyType().getId();
      this.name = service.getName();
      this.description = service.getDescription();
      this.price = service.getPrice();
   }


   public ServiceDto(Services service, ImageUploadDto file) {
      this.id = service.getId();
      this.categoryService = new CategoryServiceDto(service.getCategoryService());
      this.currencyType = new CurrencyTypeDto(service.getCurrencyType());
      this.name = service.getName();
      this.description = service.getDescription();
      this.price = service.getPrice();
      this.file = file;
   }
}
