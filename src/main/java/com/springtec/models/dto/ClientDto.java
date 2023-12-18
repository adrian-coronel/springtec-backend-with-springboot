package com.springtec.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto implements ITypeUserDTO{

   private Integer id;

   @JsonIgnore
   private User user;
   private Integer userId;
   private String name;
   private String lastname;
   private String motherLastname;
   private String dni;
   private Date birthDate;
   private byte[] file;

   public ClientDto (Client client) {
      this.id = client.getId();
      this.name = client.getName();
      this.userId = client.getUser().getId();
      this.lastname = client.getLastname();
      this.motherLastname = client.getMotherLastname();
      this.dni = client.getDni();
      this.birthDate = client.getBirthDate();
   }

   public ClientDto (Client client, byte[] file) {
      this.id = client.getId();
      this.name = client.getName();
      this.userId = client.getUser().getId();
      this.lastname = client.getLastname();
      this.motherLastname = client.getMotherLastname();
      this.dni = client.getDni();
      this.birthDate = client.getBirthDate();
      this.file = file;
   }

}
