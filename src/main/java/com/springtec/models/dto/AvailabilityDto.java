package com.springtec.models.dto;

import com.springtec.models.entity.Availability;
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
