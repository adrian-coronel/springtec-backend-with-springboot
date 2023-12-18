package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double latitude;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double longitude;
    private Date birthDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<ProfessionAvailabilityDto> professionsAvailability;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ProfessionAvailabilityDto professionAvailability;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String statusWorking;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private byte[] file;

    public TechnicalDto(Technical technical) {
        this.id = technical.getId();
        this.name = technical.getName();
        this.lastname = technical.getLastname();
        this.motherLastname = technical.getMotherLastname();
        this.dni = technical.getDni();
        this.birthDate = technical.getBirthDate();
        this.user = new UserDto( technical.getUser() );
        this.latitude = technical.getLatitude();
        this.longitude = technical.getLongitude();
        this.statusWorking = String.valueOf(technical.getWorkingStatus());
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

    public TechnicalDto(Technical technical, byte[] file) {
        this.id = technical.getId();
        this.name = technical.getName();
        this.lastname = technical.getLastname();
        this.motherLastname = technical.getMotherLastname();
        this.dni = technical.getDni();
        this.birthDate = technical.getBirthDate();
        this.user = new UserDto( technical.getUser() );
        this.latitude = technical.getLatitude();
        this.longitude = technical.getLongitude();
        this.statusWorking = String.valueOf(technical.getWorkingStatus());
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
        this.file = file;
    }


    public TechnicalDto(Technical technical, ProfessionAvailabilityDto professionAvailabilityDto) {
        this.id = technical.getId();
        this.name = technical.getName();
        this.lastname = technical.getLastname();
        this.motherLastname = technical.getMotherLastname();
        this.dni = technical.getDni();
        this.birthDate = technical.getBirthDate();
        this.user = new UserDto( technical.getUser() );
        this.latitude = technical.getLatitude();
        this.longitude = technical.getLongitude();
        this.statusWorking = String.valueOf(technical.getWorkingStatus());
        this.professionAvailability = professionAvailabilityDto;
    }


}
