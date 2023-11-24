package com.springtec.factories;

import com.springtec.auth.RegisterRequest;
import com.springtec.exceptions.DuplicateElementException;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.enums.UserType;
import com.springtec.models.repositories.ProfessionLocalRepository;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.impl.ProfessionAvailabilityImplService;
import com.springtec.services.impl.TechnicalImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TechnicalIUser implements IUser<Technical> {

   private static final UserType USER_TYPE = UserType.TECHNICAL;

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   private final TechnicalImplService technicalService;
   private final ProfessionAvailabilityImplService professionAvailabilityImplService;
   private final ProfessionLocalRepository professionLocalRepository;

   @Override
   public UserType getType() {
      return USER_TYPE;
   }

   @Override
   public User create(RegisterRequest request) throws Exception {
      filterException(request);

      User user = User.builder()
          .email(request.getEmail())
          .password(passwordEncoder.encode(request.getPassword()))
          .role(Role.builder().id(request.getRoleId()).build())
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
          .workingStatus(State.INACTIVE)
          .birthDate(request.getBirthDate())
          .build();
      technicalService.save(technical);

      return userSaved;
   }

   private void filterException(RegisterRequest request) throws Exception {
      // en caso se ingrese un email existente, ARROJAMOS nuestro exception personalizado
      if (userRepository.existsByEmail(request.getEmail()))
         throw new DuplicateElementException("El email ingresado ya existe");

      if (technicalService.existsByDni(request.getDni()))
         throw new DuplicateElementException("El dni ingresado ya existe");

   }
}
