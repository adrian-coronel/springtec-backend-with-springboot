package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.ProfessionLocal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionLocalDto {

   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer id;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer professionAvailabilityId;
   private double longitude;
   private double latitude;

   public ProfessionLocalDto(ProfessionLocal professionLocal){
      this.id = professionLocal.getId();
      this.professionAvailabilityId = professionLocal.getProfessionAvailability().getId();
      this.latitude = professionLocal.getLatitude();
      this.longitude = professionLocal.getLongitude();
   }

}
