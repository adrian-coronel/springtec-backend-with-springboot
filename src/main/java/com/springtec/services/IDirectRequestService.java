package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.payload.DirectRequestRequest;

import java.util.List;
import java.util.Map;

public interface IDirectRequestService {

   List<DirectRequestDto> findAllFiltersByTechnical(Map<String, String> filters) throws Exception;
   DirectRequestDto findById(Integer id) throws Exception;
   DirectRequestDto save(DirectRequestRequest directRequest) throws ElementNotExistInDBException;
   DirectRequestDto changeState(Integer id, DirectRequestRequest directRequestRequest) throws ElementNotExistInDBException;
}
