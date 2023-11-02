package com.springtec.factories;

import com.springtec.auth.RegisterRequest;
import com.springtec.models.dto.ITypeUserDTO;
import com.springtec.models.entity.User;

/**
 * Utilizamos el patrón FACTORY para que el código sea más escalable y flexible
 * creamos una fabrica para CONSTRUIR ENTIDADES según su ROL
 * */
public interface IUserFactory {
    User createUser(RegisterRequest request) throws Exception;

}
