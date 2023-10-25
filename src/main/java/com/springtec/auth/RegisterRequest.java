package com.springtec.auth;

import com.springtec.models.entity.Client;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.User;
import com.springtec.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Role role;

    // Atributos del cliente y tecnico
    private String name;
    private String lastname;
    private String motherLastname;
    private String dni;
    private String latitude;
    private String longitude;
    private Date birthDate;



    // Exclusive Technical
    private Integer jobId;
    private Integer availabilityId;

}
