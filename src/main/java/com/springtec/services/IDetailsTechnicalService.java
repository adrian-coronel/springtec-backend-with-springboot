package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DetailsTechnicalDto;

import java.util.List;

public interface IDetailsTechnicalService {

   List<DetailsTechnicalDto> findAll(Integer technicalId) throws ElementNotExistInDBException;

}
