package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.payload.DirectRequestRequest;

import java.util.List;

public interface IDirectRequestService {

   List<DirectRequestDto> findAllActivesByTechnicalId(Integer technicalId) throws ElementNotExistInDBException;
   DirectRequestDto findById(Integer id) throws Exception;
   DirectRequestDto save(DirectRequestRequest directRequest) throws ElementNotExistInDBException;

}
