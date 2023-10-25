package com.springtec.auth;

import com.springtec.config.JwtService;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.User;
import com.springtec.models.enums.Role;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.ClientImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ClientImplService clientService;
    //Configuramos que se use "BCryptPasswordEncoder" como codificardor de Passwords
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Metodo para registrar un usuario
     * @return token
     * */
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        var userSaved = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        if (userSaved.getRole().equals(Role.CLIENT)){
            var client = Client.builder()
                    .user(user)
                    .name(request.getName())
                    .lastname(request.getLastname())
                    .motherLastname(request.getMotherLastname())
                    .dni(request.getDni())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .birthDate(request.getBirthDate())
                    .build();
            clientService.save(client);
        }

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
