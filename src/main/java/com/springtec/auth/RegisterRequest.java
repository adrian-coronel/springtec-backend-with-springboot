package com.springtec.auth;

import com.springtec.models.dto.ProfessionAvailabilityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Esta clase sigué el patrón DTO ya que se está utilizando para encapsular
 * los datos que se envian o reciben.
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String email;
    private String password;
    private Integer roleId;

    // Atributos del cliente y tecnico
    private String name;
    private String lastname;
    private String motherLastname;
    private String dni;
    private Date birthDate;


    // Exclusive Technical
    private double latitude;
    private double longitude;

}
