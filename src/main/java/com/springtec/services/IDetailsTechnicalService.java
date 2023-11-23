package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.DetailsTechnicalDto;

import java.util.List;

public interface IDetailsTechnicalService {

   List<DetailsTechnicalDto> findAll(Integer technicalId) throws ElementNotExistInDBException;
   DetailsTechnicalDto update(Integer technicalId, Integer detailTechnicalId, DetailsTechnicalDto detailsTechnicalDto) throws ElementNotExistInDBException;
   List<DetailsTechnicalDto> updateAll(Integer technicalId, List<DetailsTechnicalDto> detailsTechnicalDtoList) throws ElementNotExistInDBException;

}
