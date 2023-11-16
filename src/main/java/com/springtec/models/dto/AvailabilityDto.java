package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.Experience;
import com.springtec.models.entity.Profession;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AvailabilityDto {
   private Integer id;
   private String name;

   public AvailabilityDto(Integer id, String name) {
      this.id = id;
      this.name = name;
   }
}
