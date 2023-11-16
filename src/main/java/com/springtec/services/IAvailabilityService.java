package com.springtec.services;

import com.springtec.models.dto.AvailabilityDto;

import java.util.List;

public interface IAvailabilityService {

   List<AvailabilityDto> findAll();

}
