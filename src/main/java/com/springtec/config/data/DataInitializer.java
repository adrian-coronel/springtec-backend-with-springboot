package com.springtec.config.data;

import com.springtec.auth.AuthenticationService;
import com.springtec.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
// CommandLineRunner tiene un metodo "run" que se ejecutará cuando la aplicación Spring Boot se inicie

    private final AuthenticationService authenticationService;

    @Override
    public void run(String... args) throws Exception {

        /*for (int i = 0 ; i < 10 ; i++){
            authenticationService.register(
                RegisterRequest.builder()
                    .email("test"+i+"@ejemplo.com")
                    .password("contraseña")
                    .roleId(2)
                    .name("test")
                    .lastname("testLastName")
                    .motherLastname("testMotherLastname")
                    .dni( generarDNI() )
                    .birthDate(new SimpleDateFormat("yyyy-MM-dd").parse("2003-05-14"))
                    .latitude(-12.0452274)
                    .longitude(-76.9494821)
                    .build()
            );
        }*/

    }

    public String generarDNI() {
        // Generar un número aleatorio de 8 dígitos para el cuerpo del DNI
        int cuerpoDNI = new Random().nextInt(99999999 - 10000000) + 10000000;

        // Calcular el dígito de control (letra) utilizando el algoritmo estándar
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int indiceLetra = cuerpoDNI % 23;
        char letraControl = letras.charAt(indiceLetra);

        // Formatear el DNI completo
        return String.format("%d%c", cuerpoDNI, letraControl);
    }
}
