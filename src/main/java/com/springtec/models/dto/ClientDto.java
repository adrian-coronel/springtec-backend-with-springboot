package com.springtec.models.dto;

import com.springtec.models.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClientDto implements ITypeUserDTO{

   private Integer id;
   private User user;
   private String name;
   private String lastname;
   private String motherLastname;
   private String dni;
   private Date birthDate;

}
