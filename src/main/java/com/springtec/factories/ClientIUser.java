package com.springtec.factories;

import com.springtec.auth.RegisterRequest;
import com.springtec.exceptions.DuplicateElementException;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.Role;
import com.springtec.models.entity.User;
import com.springtec.models.enums.State;
import com.springtec.models.enums.UserType;
import com.springtec.models.repositories.ClientRepository;
import com.springtec.models.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientIUser implements IUser<Client> {

   private static final UserType USER_TYPE = UserType.CLIENT;

   private final UserRepository userRepository;
   private final ClientRepository clientRepository;
   private final PasswordEncoder passwordEncoder;

   @Override
   public UserType getType() {
      return USER_TYPE;
   }

   @Override
   public User create(RegisterRequest request) throws Exception {
      filterException(request);

      // CREA EL USUARIO EN LA BD
      var user = User.builder()
          .email(request.getEmail())
          .password(passwordEncoder.encode(request.getPassword()))
          .role(Role.builder().id(request.getRoleId()).build())
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
         throw new DuplicateElementException("El email ingresado ya existe");

      if (clientRepository.existsByDni(request.getDni()))
         throw new DuplicateElementException("El dni ingresado ya existe");
   }


}
