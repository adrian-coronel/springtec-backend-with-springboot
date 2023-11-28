package com.springtec.models.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.DirectRequest;
import com.springtec.models.entity.ImageUpload;
import com.springtec.storage.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectRequestDto {

   private Integer id;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer clientId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private ClientDto clientDto;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer serviceTypeAvailabilityId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private ServiceTypeAvailabilityDto serviceTypeAvailabilityDto;
   private Double latitude;
   private Double longitude;
   private String title;
   private String description;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private List<ImageUploadDto> files;
   private char state;

   public DirectRequestDto(DirectRequest directRequest){
      this.id = directRequest.getId();
      this.clientId = directRequest.getId();
      this.serviceTypeAvailabilityId = directRequest.getServiceTypeAvailability() != null
          ? directRequest.getServiceTypeAvailability().getId() : null;
      this.latitude = directRequest.getLatitude();
      this.longitude = directRequest.getLongitude();
      this.title = directRequest.getTitle();
      this.description = directRequest.getDescription();
   }

   public DirectRequestDto(DirectRequest directRequest, List<ImageUploadDto> files){
      this.id = directRequest.getId();
      this.clientId = directRequest.getId();
      this.serviceTypeAvailabilityId = directRequest.getServiceTypeAvailability() != null
          ? directRequest.getServiceTypeAvailability().getId() : null;
      this.latitude = directRequest.getLatitude();
      this.longitude = directRequest.getLongitude();
      this.title = directRequest.getTitle();
      this.description = directRequest.getDescription();
      this.files = files;
   }
}
