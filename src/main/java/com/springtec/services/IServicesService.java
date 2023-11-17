package com.springtec.services;

import com.springtec.models.dto.ServiceDto;

import java.util.List;
import java.util.Map;

public interface IServicesService {

   List<ServiceDto> findByFilters(Map<String, String> filters);

}
