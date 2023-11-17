package com.springtec.models.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectRequestRequest {
   private Integer id;
   private Integer technicalId;
   private Integer clientId;
   private Integer serviceId;
   private Double latitude;
   private Double longitude;
   private String title;
   private String description;
   private List<String> imageUrls;
}
