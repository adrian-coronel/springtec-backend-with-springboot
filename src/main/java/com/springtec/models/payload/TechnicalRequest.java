package com.springtec.models.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
   @NotBlank(message = "name es obligatorio")
   private String name;
   @NotBlank(message = "lastname es obligatorio")
   private String lastname;
   @NotBlank(message = "motherLastname es obligatorio")
   private String motherLastname;
   @NotNull(message = "birthDate es obligatorio")
   private Date birthDate;
   @NotNull(message = "latitude es obligatorio")
   private Double latitude;
   @NotNull(message = "longitude es obligatorio")
   private Double longitude;
   private String workingStatus;

}
