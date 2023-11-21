package com.springtec.factories;

import com.springtec.auth.RegisterRequest;
import com.springtec.exceptions.DuplicateEmailException;
import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.entity.*;
import com.springtec.models.enums.State;
import com.springtec.models.enums.UserType;
import com.springtec.models.repositories.AvailabilityRepository;
import com.springtec.models.repositories.DetailsTechnicalRepository;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.impl.ProfessionImplService;
import com.springtec.services.impl.TechnicalImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TechnicalIUser implements IUser<Technical> {

   private static final UserType USER_TYPE = UserType.TECHNICAL;

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   private final TechnicalImplService technicalService;
   private final DetailsTechnicalRepository detailsTechnicalRepository;

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
          .birthDate(request.getBirthDate())
          .build();
      technicalService.save(technical);

      // GUARDAMOS TOD0S LOS DETALLES DE UN TECNICO
      request.getDetails().forEach( detail -> {
         detailsTechnicalRepository.save(
             new DetailsTechnical(
                 technical,
                 Availability.builder().id(detail.getAvailabilityId()).build(),
                 Profession.builder().id(detail.getAvailabilityId()).build(),
                 Experience.builder().id(detail.getExperienceId()).build(),
                 detail.getLatitude(),
                 detail.getLongitude(),
                 State.ACTIVE
             )
         );
      });

      return userSaved;
   }

   private void filterException(RegisterRequest request) throws Exception {
      // en caso se ingrese un email existente, ARROJAMOS nuestro exception personalizado
      if (userRepository.existsByEmail(request.getEmail()))
         throw new DuplicateEmailException("El email ingresado ya existe");

      if(technicalService.existsByDni(request.getDni()))
         throw new DuplicateEmailException("El dni ingresado ya existe");

      //VERIFICAMOS SI LOS DETALLES(llaves foraneas) EXISTEN
      List<String> errorsMessageList = new ArrayList<>();

      request.getDetails().forEach(detail -> {
         if (detail.getLatitude() == null)
            throw new IllegalArgumentException("La latitud no puede ser nula");
         if (detail.getLongitude() == null)
            throw new IllegalArgumentException("La longitud no puede ser nula");

         String messageErrors = detailsTechnicalRepository.existsDetailsTechnical(
             detail.getAvailabilityId(), detail.getProfessionId(), detail.getExperienceId()
         );

         if (!messageErrors.isEmpty()){ // Si hay mensajes de error
            // Divide los mensajes en un array
            String[] messageArray = messageErrors.split("; ");
            errorsMessageList.addAll(List.of(messageArray));
         }
      });
      // Si hay mensajes de error
      if (!errorsMessageList.isEmpty())
         throw new ElementNotExistInDBException(errorsMessageList.toString());
   }

}
