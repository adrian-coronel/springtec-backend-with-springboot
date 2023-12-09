package com.springtec.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    //no deben ser nulos y su longitud recortada debe ser mayor que cero
    @NotBlank(message = "email es obligatorio")
    @Email(message = "email debe no tiene el formato correcto")
    private String email;
    @NotBlank(message = "password es obligatorio")
    private String password;
    @NotBlank(message = "roleId es obligatorio")
    private Integer roleId;

    // Atributos del cliente y tecnico
    @NotBlank(message = "name es obligatorio")
    private String name;
    @NotBlank(message = "email es obligatorio")
    private String lastname;
    @NotBlank(message = "lastname es obligatorio")
    private String motherLastname;
    @NotBlank(message = "dni es obligatorio")
    @Size(message = "El campo debe tener 8 caracteres",min = 8, max = 8)
    private String dni;
    @NotBlank(message = "birthDate es obligatorio")
    private Date birthDate;


    // Exclusive Technical
    private double latitude;
    private double longitude;

}
