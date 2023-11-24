package com.springtec.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectRequestDto {

   private Integer id;
   private TechnicalDto technicalDto;
   private ClientDto clientDto;
   //todo TYPE_SERVICE_AVAILABILITY
   private Double latitude;
   private Double longitude;
   private String title;
   private String description;
   //todo LISTA DE IMG UPLOADS
   private char state;


}
