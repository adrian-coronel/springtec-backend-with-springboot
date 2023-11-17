package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.payload.DirectRequestRequest;

public interface IDirectRequestService {

   DirectRequestDto save(DirectRequestRequest directRequest) throws ElementNotExistInDBException;

}
