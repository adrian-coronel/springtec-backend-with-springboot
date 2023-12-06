package com.springtec.models.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectRequestRequest {
   private Integer id;
   private Integer professionAvailabilityId;
   private Integer clientId;
   private Integer serviceTypeAvailabilityId;
   private Integer categoryServiceId;
   private Double latitude;
   private Double longitude;
   private String title;
   private String description;
   private List<MultipartFile> imageUrls;
   private Integer stateId;
}
