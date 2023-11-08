package com.springtec.factories;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserFactory {

   //Claves son los tipos de usuario y los valores son las implementaciones
   private final Map<UserType, IUser> userMap;

   /**
    * UserFactory utiliza Spring(@Autowired) para inyectar automaticamente una lista
    * de objetos que implementan la interfaz IUser como argumento del constructor
    * */
   @Autowired
   public UserFactory(List<IUser> users) {
      userMap = users.stream().
          collect(Collectors.toUnmodifiableMap( //Se crea como inmutable, no se puede agregar o eliminar
              IUser::getType, // se utiliza para extraer el tipo de usuario de cada objeto
              //Asigna cada objeto IUser a sí mismo, lo que significa que los valores
              Function.identity())//del mapa serán las implementaciones concretas de usuarios.
          );
   }

   /**
    *  se utiliza para obtener una implementación concreta de usuario
    *  */
   public IUser getUser(UserType userType){
      return Optional.ofNullable(userMap.get(userType))
          .orElseThrow(IllegalArgumentException::new);
   }

}
