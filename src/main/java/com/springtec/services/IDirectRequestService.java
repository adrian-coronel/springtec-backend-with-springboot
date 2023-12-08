package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DirectRequestDto;
import com.springtec.models.payload.DirectRequestRequest;
import com.springtec.models.payload.StateRequest;

import java.util.List;
import java.util.Map;

public interface IDirectRequestService {

   List<DirectRequestDto> findAllFiltersByTechnical(Map<String, String> filters) throws Exception;
   DirectRequestDto findById(Integer id) throws Exception;
   DirectRequestDto save(DirectRequestRequest directRequest) throws Exception;
   DirectRequestDto changeState(Integer id, StateRequest stateRequest) throws ElementNotExistInDBException;
}
