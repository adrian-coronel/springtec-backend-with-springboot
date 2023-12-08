package com.springtec.models.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.DirectRequest;
import com.springtec.models.entity.StateDirectRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private ProfessionAvailabilityDto professionAvailability;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private CategoryServiceDto categoryService;
   private char stateInvoice;
   private Double latitude;
   private Double longitude;
   private String title;
   private String description;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private List<ImageUploadDto> files;
   private StateDirectRequest state;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Timestamp createdAt;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Timestamp answeredAt;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Timestamp resolvedAt;


   public DirectRequestDto(DirectRequest directRequest){
      this.id = directRequest.getId();
      this.clientId = directRequest.getId();
      this.serviceTypeAvailabilityId = directRequest.getServiceTypeAvailability() != null
          ? directRequest.getServiceTypeAvailability().getId() : null;
      this.categoryService = new CategoryServiceDto(directRequest.getCategoryService());
      this.latitude = directRequest.getLatitude();
      this.longitude = directRequest.getLongitude();
      this.title = directRequest.getTitle();
      this.description = directRequest.getDescription();
      this.stateInvoice = directRequest.getStateInvoice();
      this.state = directRequest.getStateDirectRequest();
   }

   public DirectRequestDto(DirectRequest directRequest, List<ImageUploadDto> files, ProfessionAvailabilityDto professionAvailability){
      this.id = directRequest.getId();
      this.clientId = directRequest.getClient().getId();
      this.serviceTypeAvailabilityId = directRequest.getServiceTypeAvailability() != null
          ? directRequest.getServiceTypeAvailability().getId() : null;
      this.professionAvailability = professionAvailability;
      this.categoryService = new CategoryServiceDto(directRequest.getCategoryService());
      this.stateInvoice = directRequest.getStateInvoice();
      this.latitude = directRequest.getLatitude();
      this.longitude = directRequest.getLongitude();
      this.title = directRequest.getTitle();
      this.description = directRequest.getDescription();
      this.state = directRequest.getStateDirectRequest();
      this.files = files;
      this.createdAt = directRequest.getCreatedAt();
      this.answeredAt = directRequest.getAnsweredAt();
      this.resolvedAt = directRequest.getResolvedAt();
   }

   public DirectRequestDto(DirectRequest directRequest, List<ImageUploadDto> files){
      this.id = directRequest.getId();
      this.clientId = directRequest.getId();
      this.serviceTypeAvailabilityId = directRequest.getServiceTypeAvailability() != null
          ? directRequest.getServiceTypeAvailability().getId() : null;
      this.categoryService = new CategoryServiceDto(directRequest.getCategoryService());
      this.stateInvoice = directRequest.getStateInvoice();
      this.latitude = directRequest.getLatitude();
      this.longitude = directRequest.getLongitude();
      this.title = directRequest.getTitle();
      this.description = directRequest.getDescription();
      this.state = directRequest.getStateDirectRequest();
      this.files = files;
      this.createdAt = directRequest.getCreatedAt();
      this.answeredAt = directRequest.getAnsweredAt();
      this.resolvedAt = directRequest.getResolvedAt();
   }
}
