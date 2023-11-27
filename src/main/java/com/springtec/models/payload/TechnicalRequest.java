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
   private Date birthDate;
   private Double latitude;
   private Double longitude;
   private String workingStatus;


}
