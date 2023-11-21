package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.DetailsTechnical;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailsTechnicalDto {

   private Integer id;
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
   private String latitude;
   private String longitude;

   public DetailsTechnicalDto(DetailsTechnical detailsTechnical){
      this.id = detailsTechnical.getId();
      this.profession = new ProfessionDto(detailsTechnical.getProfession());
      this.availability = new AvailabilityDto(detailsTechnical.getAvailability());
      this.experience = new ExperienceDto(detailsTechnical.getExperience());
      this.latitude = detailsTechnical.getLatitude();
      this.longitude = detailsTechnical.getLongitude();
   }

}
