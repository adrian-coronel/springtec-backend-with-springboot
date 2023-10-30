package com.springtec.config.data;

import com.springtec.models.entity.Availability;
import com.springtec.models.entity.Experience;
import com.springtec.models.entity.Profession;
import com.springtec.models.entity.Role;
import com.springtec.models.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
// CommandLineRunner tiene un metodo "run" que se ejecutará cuando la aplicación Spring Boot se inicie

    private final AvailabilityRepository availabilityRepository;
    private final ProfessionRepository professionRepository;
    private final RoleRepository roleRepository;
    private final ExperienceRepository experienceRepository;

    @Override
    public void run(String... args) throws Exception {
        // Código para cargar datos de prueba
        /*availabilityRepository.save(new Availability("A domicilio"));
        availabilityRepository.save(new Availability("En taller"));
        availabilityRepository.save(new Availability("Ambos"));

        professionRepository.save(new Profession("Mecanico"));
        professionRepository.save(new Profession("Plomero"));
        professionRepository.save(new Profession("Electricista"));
        professionRepository.save(new Profession("Contador"));
        professionRepository.save(new Profession("Ingeniero Civil"));

        roleRepository.save(new Role("CLIENT"));
        roleRepository.save(new Role("TECHNICAL"));

        experienceRepository.save(new Experience("Estudiante Universitario"));
        experienceRepository.save(new Experience("No titulado"));
        experienceRepository.save(new Experience("Técnico"));
        experienceRepository.save(new Experience("Ingeniero"));*/
    }
}
