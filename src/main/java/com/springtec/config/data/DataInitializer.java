package com.springtec.config.data;

import com.springtec.models.entity.Availability;
import com.springtec.models.entity.Profession;
import com.springtec.models.repositories.AvailabilityRepository;
import com.springtec.models.repositories.ProfessionRepository;
import com.springtec.models.repositories.TechnicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
// CommandLineRunner tiene un metodo "run" que se ejecutará cuando la aplicación Spring Boot se inicie

    private final AvailabilityRepository availabilityRepository;
    private final ProfessionRepository professionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Código para cargar datos de prueba
        availabilityRepository.save(new Availability("A domicilio"));
        availabilityRepository.save(new Availability("En taller"));
        availabilityRepository.save(new Availability("Ambos"));

        professionRepository.save(new Profession("Mecanico"));
        professionRepository.save(new Profession("Plomero"));
        professionRepository.save(new Profession("Electricista"));
        professionRepository.save(new Profession("Contador"));
        professionRepository.save(new Profession("Ingeniero Civil"));
    }
}
