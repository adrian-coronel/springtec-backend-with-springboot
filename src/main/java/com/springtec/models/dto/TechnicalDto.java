package com.springtec.models.dto;

import com.springtec.models.entity.Availability;
import com.springtec.models.entity.Profession;
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
public class TechnicalDto {

    private Integer id;
    private String name;
    private String lastname;
    private String motherLastname;
    private String dni;
    private String latitude;
    private String longitude;
    private Date birthDate;
    private User user;
    private Set<Profession> professions;
    private Availability availability;
}
