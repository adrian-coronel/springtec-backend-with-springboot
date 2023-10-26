package com.springtec.factories;

import com.springtec.auth.RegisterRequest;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.User;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.impl.TechnicalImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class TechnicalFactory implements IUserFactory{


    private final UserRepository userRepository;
    private final TechnicalImplService technicalService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(RegisterRequest request) {

        // CREA LA ENTIDAD USUARIO EN LA BD
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
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
                .availabilityId(request.getAvailabilityId())
                .jobId(request.getJobId())
                .build();
        technicalService.save(technical);

        return userSaved;
    }
}
