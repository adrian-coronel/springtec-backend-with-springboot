package com.springtec.models.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "technicalId no puede ser nulo")
    private Integer technicalId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = "professionAvailabilityId no puede ser nulo")
    private Integer professionAvailabilityId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = "categoryServiceId no puede ser nulo")
    private Integer categoryServiceId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = "currencyTypeId no puede ser nulo")
    private Integer currencyTypeId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "name es obligatorio")
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "description es obligatorio")
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = "price no puede ser nulo")
    private Double price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MultipartFile file;


}