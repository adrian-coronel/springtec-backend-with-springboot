package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.Experience;
import com.springtec.models.entity.Profession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProfessionDto {

   private Integer id;
   private String name;

   // Este campo se incluirá solo si no es nulo
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Experience experience;

   public ProfessionDto(Profession profession, Experience experience) {
      this.id = profession.getId();
      this.name = profession.getName();
      this.experience = experience;
   }

   public ProfessionDto(Integer id, String name) {
      this.id = id;
      this.name = name;
   }
}
