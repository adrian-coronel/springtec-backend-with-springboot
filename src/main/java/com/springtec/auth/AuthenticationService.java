package com.springtec.auth;

import com.springtec.config.JwtService;
import com.springtec.factories.ClientFactory;
import com.springtec.factories.IUserFactory;
import com.springtec.factories.TechnicalFactory;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.User;
import com.springtec.models.enums.Role;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.impl.ClientImplService;
import com.springtec.services.impl.TechnicalImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ClientImplService clientService;
    private final TechnicalImplService technicalService;
    //Configuramos que se use "BCryptPasswordEncoder" como codificardor de Passwords
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Metodo para registrar un usuario
     * @return token
     * */
    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        // Creamos un tipo de USERFACTORY dependiendo del ROL
        IUserFactory userFactory = getUserFactory(request.getRole());
        // Una vez tenemos el TIPO, le pasamos los datos enviados
        User userSaved = userFactory.createUser(request);
        // Generamos el token con el usuario guardado
        String jwtToken = jwtService.generateToken(userSaved);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Este metódo buscará crear algún tipo de USERFACTORY dependiendo de su ROL
     * */
    private IUserFactory getUserFactory(Role role) throws Exception {
        switch (role) {
            case CLIENT -> {
                return new ClientFactory(userRepository,clientService,passwordEncoder);
            }
            case TECHNICAL -> {
                return new TechnicalFactory(userRepository,technicalService,passwordEncoder);
            }
            default -> throw new Exception();
        }
    }
}
