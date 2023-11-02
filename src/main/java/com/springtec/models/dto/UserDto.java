package com.springtec.models.dto;

import com.springtec.models.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

   private Integer id;
   private String email;
   private Role role;
   private char state;
   private ITypeUserDTO typeUserDto;

}
