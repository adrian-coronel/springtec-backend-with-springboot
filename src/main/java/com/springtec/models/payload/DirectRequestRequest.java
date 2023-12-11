package com.springtec.models.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectRequestRequest {
   private Integer id;
   @NotNull(message = "professionAvailabilityId no puede ser nulo")
   private Integer professionAvailabilityId;
   @NotNull(message = "clientId no puede ser nulo")
   private Integer clientId;
   private Integer serviceTypeAvailabilityId;
   @NotNull(message = "categoryServiceId no puede ser nulo")
   private Integer categoryServiceId;
   @NotNull(message = "latitude no puede ser nulo")
   private Double latitude;
   @NotNull(message = "longitud no puede ser nulo")
   private Double longitude;
   @NotBlank(message = "titulo es obligatorio")
   private String title;
   @NotBlank(message = "descripcion es obligatorio")
   private String description;
   private List<MultipartFile> imageUrls;
   private Integer stateId;

   /**
    * Cuando no se envian imagenes se recibe un array de string vacio, para solucionar esto
    * esperamos una Lista anonima, en caso no sea vacia, se casteará a una Lista de Files y
    * sino a una lista vacia
    * */
   public void setImageUrls(List<?> imageUrls) {
      if (!imageUrls.isEmpty())
         this.imageUrls = (List<MultipartFile>) imageUrls;
      else
         this.imageUrls =  new ArrayList<>();
   }

}
