package com.springtec.models.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
   @NotBlank(message = "latitude es obligatorio")
   private Double latitude;
   @NotBlank(message = "longitude es obligatorio")
   private Double longitude;
}
