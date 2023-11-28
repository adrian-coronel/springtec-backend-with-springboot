package com.springtec.models.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest {

   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer id;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer technicalId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer availabilityId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer professionId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer categoryServiceId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Integer currencyTypeId;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private String name;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private String description;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private Double price;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private MultipartFile file;

}
