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

   public ProfessionDto(Profession profession){
      this.id = profession.getId();
      this.name = profession.getName();
   }

}
