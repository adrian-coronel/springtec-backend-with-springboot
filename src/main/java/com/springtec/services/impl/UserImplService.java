package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ITypeUserDTO;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.dto.UserDto;
import com.springtec.models.entity.User;
import com.springtec.models.repositories.RoleRepository;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImplService implements IUserService {

   private final UserRepository userRepository;
   private final TechnicalImplService technicalService;

   @Override
   public List<UserDto> findAll() {
      return null;
   }

   @Override
   public ITypeUserDTO findById(Integer id) throws ElementNotExistInDBException {
      User user = userRepository.findById(id)
          .orElseThrow(() -> new ElementNotExistInDBException("No existe el usuario con id -> "+ id));
      return getTypeUser(user);
   }

   private ITypeUserDTO getTypeUser(User user) {
      switch ( user.getRole().getName().toUpperCase() ) {
         case "TECHNICAL":
            return technicalService.findByUser(user);
         case "CLIENT":
            // todo COMPLETAR
            return null;
         default:
            // todo CONTROLAR AQUI CON UN ERROR
            return null;
      }
   }

   @Override
   public UserDto save(User user) {
      return null;
   }

   @Override
   public UserDto update(User user) {
      return null;
   }

   @Override
   public UserDto deleteById(Integer id) {
      return null;
   }

   @Override
   public boolean existsById(Integer id) {
      return false;
   }
}
