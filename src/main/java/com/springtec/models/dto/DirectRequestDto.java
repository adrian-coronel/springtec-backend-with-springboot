package com.springtec.models.dto;

import com.springtec.models.entity.DirectRequest;
import com.springtec.models.entity.ImgFirebase;
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
   private ServiceDto serviceDto;
   private Double latitude;
   private Double longitude;
   private String title;
   private String description;
   private List<ImgFirebase> imageUrls;
   private char state;


}
