package com.springtec.models.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalRequest {

   private Integer id;
   private String name;
   private String lastname;
   private String motherLastname;
   private String dni;
   private String latitude;
   private String longitude;
   private Date birthDate;
   private List<TechnicalProfessionRequest> professions;
   private Integer availabilityId;

}