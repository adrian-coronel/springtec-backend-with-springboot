package com.springtec.models.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
   @NotNull(message = "latitude es obligatorio")
   private Double latitude;
   @NotNull(message = "longitude es obligatorio")
   private Double longitude;
}
