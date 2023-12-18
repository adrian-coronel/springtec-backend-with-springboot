package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ITypeUserDTO;
import com.springtec.models.dto.ImageUploadDto;
import com.springtec.models.dto.UserDto;
import com.springtec.models.payload.UserRequest;

import java.util.List;

public interface IUserService {

   List<UserDto> findAll();
   ImageUploadDto getImageByUserId(Integer id);
   ITypeUserDTO findById(Integer id) throws ElementNotExistInDBException;
   void update(UserRequest user, Integer id) throws Exception;
   UserDto deleteById(Integer id);
   boolean existsById(Integer id);

}
