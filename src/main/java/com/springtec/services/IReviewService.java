package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ReviewDto;

import java.util.List;
import java.util.Map;

public interface IReviewService {
   List<ReviewDto> findAllByFilters(Map<String, String> filters) throws ElementNotExistInDBException;
   ReviewDto findById(Integer id) throws ElementNotExistInDBException;
   ReviewDto save(ReviewDto reviewDto) throws ElementNotExistInDBException;

}
