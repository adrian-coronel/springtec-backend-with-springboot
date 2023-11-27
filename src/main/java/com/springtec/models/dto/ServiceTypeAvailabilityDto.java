package com.springtec.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTypeAvailabilityDto {

   private Integer id;
   private Integer serviceId;
   private Integer professionAvailabilityId;

}
