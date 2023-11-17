package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.Availability;
import com.springtec.models.entity.Profession;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.User;
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
public class TechnicalDto implements ITypeUserDTO{

    private Integer id;
    private String name;
    private String lastname;
    private String motherLastname;
    private String dni;
    private String latitude;
    private String longitude;
    private Date birthDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User user;
    private Set<ProfessionDto> professions;
    private Availability availability;

    public TechnicalDto(Technical technical) {
        this.id = technical.getId();
        this.name = technical.getName();
        this.lastname = technical.getLastname();
        this.motherLastname = technical.getMotherLastname();
        this.dni = technical.getDni();
        this.birthDate = technical.getBirthDate();
        this.latitude = technical.getLatitude();
        this.longitude = technical.getLongitude();
        this.user = technical.getUser();
    }

    public TechnicalDto(Technical technical, Set<ProfessionDto> professions) {
        this.id = technical.getId();
        this.name = technical.getName();
        this.lastname = technical.getLastname();
        this.motherLastname = technical.getMotherLastname();
        this.dni = technical.getDni();
        this.birthDate = technical.getBirthDate();
        this.latitude = technical.getLatitude();
        this.longitude = technical.getLongitude();
        this.user = technical.getUser();
        this.professions = professions;
        this.availability = technical.getAvailability();
    }


}
