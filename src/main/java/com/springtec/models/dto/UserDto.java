package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springtec.models.entity.Role;
import com.springtec.models.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

   private Integer id;
   private String email;
   private Role role;
   @JsonInclude( JsonInclude.Include.NON_NULL )
   private char state;
   @JsonInclude( JsonInclude.Include.NON_NULL )
   private ITypeUserDTO typeUserDto;

   public UserDto(User user) {
      this.id = user.getId();;
      this.email = user.getEmail();;
      this.role = user.getRole();
   }

}
