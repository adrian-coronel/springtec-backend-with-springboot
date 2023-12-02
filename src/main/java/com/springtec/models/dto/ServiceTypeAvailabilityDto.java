package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.ProfessionAvailability;
import com.springtec.models.entity.ProfessionLocal;
import com.springtec.models.entity.ServiceTypeAvailability;
import com.springtec.models.entity.Services;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTypeAvailabilityDto {

    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer serviceId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ServiceDto service;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer professionAvailabilityId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfessionAvailabilityDto professionAvailability;

    public ServiceTypeAvailabilityDto(ServiceTypeAvailability serviceTypeAvailability){
        this.id = serviceTypeAvailability.getId();
        this.service = new ServiceDto(serviceTypeAvailability.getServices());
        this.professionAvailability = new ProfessionAvailabilityDto(serviceTypeAvailability.getProfessionAvailability());
    }

    public ServiceTypeAvailabilityDto(ServiceTypeAvailability serviceTypeAvailability, ProfessionLocal professionLocal){
        this.id = serviceTypeAvailability.getId();
        this.service = new ServiceDto(serviceTypeAvailability.getServices());
        this.professionAvailability = new ProfessionAvailabilityDto(
            serviceTypeAvailability.getProfessionAvailability(),
            professionLocal.getLatitude(),
            professionLocal.getLongitude());
    }

    public ServiceTypeAvailabilityDto(Integer id, ProfessionAvailabilityDto professionAvailability, ServiceDto service){
        this.id = id;
        this.professionAvailability = professionAvailability;
        this.service = service;
    }
}