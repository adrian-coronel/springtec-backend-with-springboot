package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.payload.DirectRequestRequest;

import java.util.List;

public interface IDirectRequestService {

   List<DirectRequestDto> findAllByTechnical(Integer technicalId);
   List<DirectRequestDto> findAllActivesByTechnicalId(Integer technicalId);
   DirectRequestDto findById(Integer id);
   DirectRequestDto save(DirectRequestRequest directRequest) throws ElementNotExistInDBException;

}
