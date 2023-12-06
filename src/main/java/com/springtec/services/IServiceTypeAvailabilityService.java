package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ServiceTypeAvailabilityDto;

import java.util.List;
import java.util.Map;

public interface IServiceTypeAvailabilityService {

   List<ServiceTypeAvailabilityDto> findByFilters(Map<String, String> filters) throws ElementNotExistInDBException;

}
