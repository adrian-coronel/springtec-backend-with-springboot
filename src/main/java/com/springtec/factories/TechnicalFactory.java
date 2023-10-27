package com.springtec.factories;

import com.springtec.auth.RegisterRequest;
import com.springtec.exceptions.DuplicateEmailException;
import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.entity.Availability;
import com.springtec.models.entity.Profession;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.User;
import com.springtec.models.repositories.AvailabilityRepository;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.impl.ProfessionImplService;
import com.springtec.services.impl.TechnicalImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class TechnicalFactory implements IUserFactory{


    private final UserRepository userRepository;
    private final AvailabilityRepository availabilityRepository;
    private final TechnicalImplService technicalService;
    private final ProfessionImplService professionService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(RegisterRequest request) throws DuplicateEmailException, ElementNotExistInDBException {
        System.out.println(request);
        // en caso se ingrese un email existente, ARROJAMOS nuestro exception personalizado
        if (userRepository.existsByEmail(request.getEmail()))
            throw new DuplicateEmailException("El email ingresado ya existe");

        // BUSCA EL ELEMENTO Y SI NO EXISTE ARROJA NUESTRA EXCEPTION PERSONALIZADA
        if (!availabilityRepository.existsById(request.getAvailabilityId()))
            throw new ElementNotExistInDBException("El elemento avaiability con id="+request.getAvailabilityId()+" no existe");

        // SI LAS PROFESIONES ALCANSADAS NO EXISTEN
        if (!professionService.existsAllById(request.getProfessionsId()))
            throw new ElementNotExistInDBException("La profesión que ingresaste no existe");

        // CREA LA ENTIDAD USUARIO EN LA BD
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        User userSaved = userRepository.save(user);


        // Recibimos las profesiones y las pasamos en un objeto SET
        Set<Profession> professions = new HashSet<>();
        for (Integer id : request.getProfessionsId()) {
            professions.add(Profession.builder().id(id).build());
        }
        // CREA LA ENTIDAD TECNICO CON RELACION A UN USUARIO
        Technical technical = Technical.builder()
                .user(userSaved)
                .name(request.getName())
                .lastname(request.getLastname())
                .motherLastname(request.getMotherLastname())
                .dni(request.getDni())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .birthDate(request.getBirthDate())
                .availability(Availability.builder().id(request.getAvailabilityId()).build())
                .professions(professions)
                .build();
        technicalService.save(technical);

        return userSaved;
    }
}
