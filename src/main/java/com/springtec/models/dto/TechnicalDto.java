package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalDto implements ITypeUserDTO{

    private Integer id;
    private String name;
    private String lastname;
    private String motherLastname;
    private String dni;

    private Date birthDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;
    List<ProfessionAvailabilityDto> professionsAvailability;

    public TechnicalDto(Technical technical) {
        this.id = technical.getId();
        this.name = technical.getName();
        this.lastname = technical.getLastname();
        this.motherLastname = technical.getMotherLastname();
        this.dni = technical.getDni();
        this.birthDate = technical.getBirthDate();
        this.user = new UserDto( technical.getUser() );
        this.professionsAvailability = technical.getProfessionsAvailability()
            .stream()
            .map( pA -> ProfessionAvailabilityDto
                .builder()
                .id(pA.getId())
                .profession( new ProfessionDto(pA.getProfession()) )
                .availability( new AvailabilityDto(pA.getAvailability()) )
                .experience( new ExperienceDto(pA.getExperience()) )
                .build()
            )
            .toList();
    }




}
