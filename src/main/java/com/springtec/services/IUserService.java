package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ITypeUserDTO;
import com.springtec.models.dto.UserDto;
import com.springtec.models.entity.User;

import java.util.List;

public interface IUserService {

   List<UserDto> findAll();

   ITypeUserDTO findById(Integer id) throws ElementNotExistInDBException;
   UserDto save(User user);
   UserDto update(User user);
   UserDto deleteById(Integer id);
   boolean existsById(Integer id);

}
