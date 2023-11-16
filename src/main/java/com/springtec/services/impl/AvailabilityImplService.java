package com.springtec.services.impl;

import com.springtec.models.dto.AvailabilityDto;
import com.springtec.models.repositories.AvailabilityRepository;
import com.springtec.services.IAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityImplService implements IAvailabilityService {

   private final AvailabilityRepository availabilityRepository;

   @Override
   public List<AvailabilityDto> findAll() {
      return availabilityRepository.findAll()
          .stream()
          .map(availability -> new AvailabilityDto(availability.getId(),availability.getName()))
          .toList();
   }
}
