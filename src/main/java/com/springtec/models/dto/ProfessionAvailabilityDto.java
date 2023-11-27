package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.ProfessionAvailability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionAvailabilityDto {

   private Integer id;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer technicalId;
   @JsonIgnore
   private TechnicalDto technical;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer professionId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private ProfessionDto profession;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer availabilityId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private AvailabilityDto availability;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer experienceId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private ExperienceDto experience;

   // DISPONIBILIDAD EN TALLER
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Double latitude;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Double longitude;

   public ProfessionAvailabilityDto(ProfessionAvailability professionAvailability){
      this.id = professionAvailability.getId();
      this.technical = new TechnicalDto(professionAvailability.getTechnical());
      this.profession = new ProfessionDto(professionAvailability.getProfession());
      this.availability = new AvailabilityDto(professionAvailability.getAvailability());
      this.experience = new ExperienceDto(professionAvailability.getExperience());
   }
   public ProfessionAvailabilityDto(ProfessionAvailability professionAvailability, Double latitude, Double longitude){
      this.id = professionAvailability.getId();
      this.technical = new TechnicalDto(professionAvailability.getTechnical());
      this.profession = new ProfessionDto(professionAvailability.getProfession());
      this.availability = new AvailabilityDto(professionAvailability.getAvailability());
      this.experience = new ExperienceDto(professionAvailability.getExperience());
      this.latitude = latitude;
      this.longitude = longitude;
   }


}