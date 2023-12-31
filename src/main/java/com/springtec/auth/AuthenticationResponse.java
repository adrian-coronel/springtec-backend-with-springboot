package com.springtec.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase sigué el patrón DTO ya que se está utilizando para encapsular
 * los datos que se envian o reciben.
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token; // Token que se enviará al usuario

}
