package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.StateDirectRequest;
import com.springtec.models.payload.DirectRequestView;
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
public class DirectRequestViewDto {
   private Integer id;
   private Integer clientId;
   private ClientDto clientDto;
   private Integer serviceTypeAvailabilityId;
   private ServiceTypeAvailabilityDto serviceTypeAvailabilityDto;
   private ProfessionAvailabilityDto professionAvailability;
   private CategoryServiceDto categoryService;
   private char stateInvoice;
   private Double latitude;
   private Double longitude;
   private String title;
   private String description;
   private List<ImageUploadDto> files;
   private StateDirectRequest state;
   private Timestamp createdAt;
   private Timestamp answeredAt;
   private Timestamp resolvedAt;

   public DirectRequestViewDto(DirectRequestView directRequestView){
      this.id = directRequestView.getDrId();
      this.clientDto = ClientDto.builder()
          .id(directRequestView.getCId())
          .userId(directRequestView.getCUserId())
          .name(directRequestView.getCName())
          .lastname(directRequestView.getCFatherLastname())
          .motherLastname(directRequestView.getCMotherLastname())
          .dni(directRequestView.getCDni())
          .birthDate(directRequestView.getCBirthDate())
          .build();
      this.professionAvailability = ProfessionAvailabilityDto.builder()
          .id(directRequestView.getPaId())
          .profession(
              ProfessionDto.builder()
                  .id(directRequestView.getPId())
                  .name(directRequestView.getPName())
                  .build()
          )
          .availability(
              AvailabilityDto.builder()
                  .id(directRequestView.getAId())
                  .name(directRequestView.getAName())
                  .build()
          )
          .experience(
              ExperienceDto.builder()
                  .id(directRequestView.getEId())
                  .name(directRequestView.getEName())
                  .build()
          )
          .build();
      this.categoryService = CategoryServiceDto.builder()
          .id(directRequestView.getCsId())
          .name(directRequestView.getCsName())
          .state(directRequestView.getCsState())
          .build();
      this.stateInvoice = directRequestView.getDrStateInvoice();
      this.latitude = directRequestView.getDrLatitude();
      this.longitude = directRequestView.getDrLongitude();
      this.title = directRequestView.getDrTitle();
      this.description = directRequestView.getDrDescription();
      this.createdAt = directRequestView.getDrCreatedAt();
      //todo AGREGAR LOS DEMAS fechas.._at

   }

   public DirectRequestViewDto(DirectRequestView directRequestView, List<ImageUploadDto> files){
      this.id = directRequestView.getDrId();
      this.clientDto = ClientDto.builder()
          .id(directRequestView.getCId())
          .userId(directRequestView.getCUserId())
          .name(directRequestView.getCName())
          .lastname(directRequestView.getCFatherLastname())
          .motherLastname(directRequestView.getCMotherLastname())
          .dni(directRequestView.getCDni())
          .birthDate(directRequestView.getCBirthDate())
          .build();
      this.professionAvailability = ProfessionAvailabilityDto.builder()
          .id(directRequestView.getPaId())
          .profession(
              ProfessionDto.builder()
                  .id(directRequestView.getPId())
                  .name(directRequestView.getPName())
                  .build()
          )
          .availability(
              AvailabilityDto.builder()
                  .id(directRequestView.getAId())
                  .name(directRequestView.getAName())
                  .build()
          )
          .experience(
              ExperienceDto.builder()
                  .id(directRequestView.getEId())
                  .name(directRequestView.getEName())
                  .build()
          )
          .build();
      this.categoryService = CategoryServiceDto.builder()
          .id(directRequestView.getCsId())
          .name(directRequestView.getCsName())
          .state(directRequestView.getCsState())
          .build();
      this.stateInvoice = directRequestView.getDrStateInvoice();
      this.latitude = directRequestView.getDrLatitude();
      this.longitude = directRequestView.getDrLongitude();
      this.title = directRequestView.getDrTitle();
      this.description = directRequestView.getDrDescription();
      this.createdAt = directRequestView.getDrCreatedAt();
      //todo AGREGAR LOS DEMAS fechas.._at
      this.files = files;
   }

}
