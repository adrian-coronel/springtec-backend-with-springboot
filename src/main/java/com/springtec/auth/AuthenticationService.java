package com.springtec.auth;

import com.springtec.config.JwtService;
import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.factories.ClientFactory;
import com.springtec.factories.IUserFactory;
import com.springtec.factories.TechnicalFactory;
import com.springtec.models.entity.Role;
import com.springtec.models.entity.User;
import com.springtec.models.repositories.AvailabilityRepository;
import com.springtec.models.repositories.RoleRepository;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.impl.ClientImplService;
import com.springtec.services.impl.ProfessionImplService;
import com.springtec.services.impl.TechnicalImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AvailabilityRepository availabilityRepository;
    private final ClientImplService clientService;
    private final TechnicalImplService technicalService;
    private final ProfessionImplService professionService;
    //Configuramos que se use "BCryptPasswordEncoder" como codificardor de Passwords
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Metodo para registrar un usuario
     * @return token
     * */
    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        // Creamos un tipo de USERFACTORY dependiendo del ROL
        IUserFactory userFactory = getUserFactory(request.getRoleId());
        // Una vez tenemos el TIPO, le pasamos los datos enviados
        User userSaved = userFactory.createUser(request);
        // Generamos el token con el usuario guardado
        String jwtToken = jwtService.generateToken(userSaved);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Metodo para autenticar un usuario
     * @return token
     * */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Usamos el Administrador de Autentificacion para AUTENTICAR AL USUARIO
        authenticationManager.authenticate(
                //El administrador realiza tod0 el trabajo
                // En caso exista un error se arrojará un excepción
                new UsernamePasswordAuthenticationToken(
                        // Verifica los datos con los datos de la BD, en caso no coincide
                        // lanzará una exception, qeu indica error de autenticatión
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Buscamos al usuario y generamos un token con sus detalles
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user); // generamos el token para el usuario

        return AuthenticationResponse.builder()
                // Retornamos el TOKEN contenido por "AuthenticationResponse"
                .token(jwtToken)
                .build();
    }


    /**
     * Este metódo buscará crear algún tipo de USERFACTORY dependiendo de su ROL
     * */
    private IUserFactory getUserFactory(Integer roleId) throws Exception {
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new ElementNotExistInDBException("El rol no existe con ID -> "+ roleId));

        switch (role.getName().toUpperCase()) {
            case "CLIENT" -> {
                return new ClientFactory(role,userRepository,clientService,passwordEncoder);
            }
            case "TECHNICAL" -> {
                return new TechnicalFactory(role,userRepository, availabilityRepository,technicalService,professionService,passwordEncoder);
            }
            default -> throw new Exception();
        }
    }
}
