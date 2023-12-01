package com.springtec.config.data;

import com.springtec.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
// CommandLineRunner tiene un metodo "run" que se ejecutará cuando la aplicación Spring Boot se inicie

    private final AuthenticationService authenticationService;

    @Override
    public void run(String... args) throws Exception {

    }
}
