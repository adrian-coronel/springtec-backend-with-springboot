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
   @NotNull(message = "latitude no puede ser null")
   private Double latitude;
   @NotNull(message = "longitude no puede ser null")
   private Double longitude;
}
