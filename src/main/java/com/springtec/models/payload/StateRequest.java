package com.springtec.models.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StateRequest {

   @NotBlank(message = "state es obligatorio")
   @Size(message = "state solo debe contener 1 caracter",min = 1,max = 1)
   private Integer stateId;

}
