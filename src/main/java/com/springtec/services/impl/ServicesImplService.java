package com.springtec.services.impl;

import com.springtec.models.dto.ServiceDto;
import com.springtec.models.entity.Services;
import com.springtec.models.repositories.ServiceRepository;
import com.springtec.services.IServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ServicesImplService implements IServicesService {

   private final ServiceRepository serviceRepository;

   @Override
   public List<ServiceDto> findByFilters(Map<String, String> filters) {
      return null;
   }

   private List<ServiceDto> mapServiceToServiceDto(List<Services> servicesList){
      return servicesList
          .stream()
          .map(ServiceDto::new)
          .toList();
   }
}
