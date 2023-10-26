package com.springtec.factories;

import com.springtec.auth.RegisterRequest;
import com.springtec.exceptions.DuplicateEmailException;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.User;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.impl.ClientImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Esta clasese utilizará para crear un usuario CLIENTE
 * */
@RequiredArgsConstructor
public class ClientFactory implements IUserFactory{


    private final UserRepository userRepository;
    private final ClientImplService clientService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User createUser(RegisterRequest request) throws DuplicateEmailException {
        // en caso se ingrese un email existente, ARROJAMOS nuestro exception personalizado
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("El email ingresado ya existe");
        }
        // CREA EL USUARIO EN LA BD
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var userSaved = userRepository.save(user);

        // CREA LA ENTIDAD CLIENTE DEPENDINDO
        Client client = Client.builder()
                .user(userSaved)
                .name(request.getName())
                .lastname(request.getLastname())
                .motherLastname(request.getMotherLastname())
                .dni(request.getDni())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .birthDate(request.getBirthDate())
                .build();
        clientService.save(client);

        return userSaved;
    }
}
