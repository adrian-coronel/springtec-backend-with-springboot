package com.springtec.models.dto;

import com.springtec.models.entity.Experience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProfessionDto {

   private Integer id;
   private String name;
   private Experience experience;

}
