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
   private Integer technicalId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private TechnicalDto technicalDto;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer professionId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private ProfessionDto professionDto;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer categoryServieId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private CategoryServiceDto categoryServiceDto;
   private String name;
   private double price;
   private String urlImage;
   private char state;

   public ServiceDto(Services service) {
      this.id = service.getId();
      this.technicalId = service.getTechnical().getId();
      this.professionId = service.getProfession().getId();
      this.categoryServieId = service.getCategoryService().getId();
      this.name = service.getName();
      this.price = service.getPrice();
      this.urlImage = service.getUrlImage();
   }
}
