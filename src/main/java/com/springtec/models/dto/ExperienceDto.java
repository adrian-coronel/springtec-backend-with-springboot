package com.springtec.models.dto;

import com.springtec.models.entity.Experience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {

   private Integer id;
   private String name;

   public ExperienceDto(Experience experience) {
      this.id = experience.getId();
      this.name = experience.getName();
   }

}
