package com.springtec.factories;

import com.springtec.auth.RegisterRequest;
import com.springtec.exceptions.DuplicateEmailException;
import com.springtec.models.dto.ITypeUserDTO;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.Role;
import com.springtec.models.entity.User;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.ClientRepository;
import com.springtec.models.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Esta clasese utilizará para crear un usuario CLIENTE
 * */
@RequiredArgsConstructor
public class ClientFactory implements IUserFactory{

    private final Role role;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User createUser(RegisterRequest request) throws Exception {

        filterException(request);

        // CREA EL USUARIO EN LA BD
        var user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(role)
            .state(State.ACTIVE)
            .build();
        var userSaved = userRepository.save(user);

        // CREA LA ENTIDAD CLIENTE DEPENDINDO
        Client client = Client.builder()
            .user(userSaved)
            .name(request.getName())
            .lastname(request.getLastname())
            .motherLastname(request.getMotherLastname())
            .dni(request.getDni())
            .birthDate(request.getBirthDate())
            .build();
        clientRepository.save(client);

        return userSaved;
    }


    private void filterException(RegisterRequest request) throws Exception{
        // en caso se ingrese un email existente, ARROJAMOS nuestro exception personalizado
        if (userRepository.existsByEmail(request.getEmail()))
            throw new DuplicateEmailException("El email ingresado ya existe");

        if (clientRepository.existsByDni(request.getDni()))
            throw new DuplicateEmailException("El dni ingresado ya existe");
    }
}
