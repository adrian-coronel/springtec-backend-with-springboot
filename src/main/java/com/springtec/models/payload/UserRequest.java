package com.springtec.models.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

   private Integer id;
   private String email;
   private String password; // UNICO ATRIBUTO ACTUALIZABLE
   private Integer roleId;
   private char state;

}
