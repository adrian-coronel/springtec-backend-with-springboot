package com.springtec.factories;

import com.springtec.auth.RegisterRequest;
import com.springtec.exceptions.DuplicateEmailException;
import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ITypeUserDTO;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.AvailabilityRepository;
import com.springtec.models.repositories.TechnicalProfessionRepository;
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

    private final Role role;
    private final UserRepository userRepository;
    private final AvailabilityRepository availabilityRepository;
    private final TechnicalProfessionRepository technicalProfessionRepository;
    private final TechnicalImplService technicalService;
    private final ProfessionImplService professionService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(RegisterRequest request) throws Exception {

        filterException(request);

        // CREA LA ENTIDAD USUARIO EN LA BD
        User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(role)
            .state(State.ACTIVE)
            .build();
        User userSaved = userRepository.save(user);

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
            .build();
        technicalService.save(technical);


       // Recibimos las profesiones y experiencias y las GUARDAMOS junto al TECNICO
       for (var professionDto : request.getProfessions()) {
          // SI EL ID DE LA PROFESSION NO SE ENCUETRA EN LA BD
          if (!professionService.existsById(professionDto.getProfessionId()))
             throw new ElementNotExistInDBException("La profesion no existe con Id -> "+ professionDto.getProfessionId());

          technicalProfessionRepository.save(
              TechnicalProfession.builder()
                  .technical(technical)
                  .profession(
                      Profession.builder()
                          .id(professionDto.getProfessionId())
                          .build()
                  )
                  .experience(
                      Experience.builder()
                          .id(professionDto.getExperienceId())
                          .build()
                  )
                  .build()
          );
       }

        return userSaved;
    }


   private void filterException(RegisterRequest request) throws Exception {
        // en caso se ingrese un email existente, ARROJAMOS nuestro exception personalizado
        if (userRepository.existsByEmail(request.getEmail()))
            throw new DuplicateEmailException("El email ingresado ya existe");

        if(technicalService.existsByDni(request.getDni()))
            throw new DuplicateEmailException("El dni ingresado ya existe");

        // BUSCA EL ELEMENTO Y SI NO EXISTE ARROJA NUESTRA EXCEPTION PERSONALIZADA
        if (!availabilityRepository.existsById(request.getAvailabilityId()))
            throw new ElementNotExistInDBException("El elemento avaiability con id="+request.getAvailabilityId()+" no existe");

    }


}
