package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.Availability;
import com.springtec.models.entity.Experience;
import com.springtec.models.entity.Profession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {
   private Integer id;
   private String name;

   public AvailabilityDto(Availability availability){
      this.id = availability.getId();
      this.name = availability.getName();
   }

}
