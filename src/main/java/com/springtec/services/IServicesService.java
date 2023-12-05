package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.exceptions.InvalidArgumentException;
import com.springtec.models.dto.ServiceDto;
import com.springtec.models.payload.ServiceRequest;

import java.util.List;
import java.util.Map;

public interface IServicesService {

   List<ServiceDto> findByFilters(Map<String, String> filters) throws Exception;
   ServiceDto findById(Integer id) throws ElementNotExistInDBException;
   ServiceDto save(ServiceRequest serviceRequest) throws ElementNotExistInDBException;
   ServiceDto udpate(Integer serviceId, ServiceRequest serviceRequest) throws ElementNotExistInDBException;

}
