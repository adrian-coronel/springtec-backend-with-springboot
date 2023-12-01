package com.springtec.factories;

import com.springtec.auth.RegisterRequest;
import com.springtec.models.entity.User;
import com.springtec.models.enums.UserType;

public interface IUser<T> {
   UserType getType();
   User create(RegisterRequest request) throws Exception;
}
