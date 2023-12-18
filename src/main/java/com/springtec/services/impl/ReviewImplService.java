package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ReviewDto;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.Review;
import com.springtec.models.entity.Technical;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.ClientRepository;
import com.springtec.models.repositories.ReviewRepository;
import com.springtec.models.repositories.TechnicalRepository;
import com.springtec.services.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewImplService implements IReviewService {

   private final ReviewRepository reviewRepository;
   private final TechnicalRepository technicalRepository;
   private final ClientRepository clientRepository;

   @Override
   public List<ReviewDto> findAllByFilters(Map<String,String> filters) throws ElementNotExistInDBException {
      filterException(filters);

      List<Review> reviewList;
      if (filters.containsKey("technicalId")){
         reviewList = reviewRepository.findAllByTechnicalId(Integer.parseInt(filters.get("technicalId")));
      }
      else if (filters.containsKey("clientId")){
         reviewList = reviewRepository.findAllByClientId(Integer.parseInt(filters.get("clientId")));
      }
      else{
         reviewList = reviewRepository.findAll();
      }

      return reviewList.stream()
          .map(ReviewDto::new)
          .toList();
   }

   private void filterException(Map<String, String> filters) throws ElementNotExistInDBException {
      if (filters.containsKey("technicalId")
          && !technicalRepository.existsByIdAndUserState(Integer.parseInt(filters.get("technicalId")), State.ACTIVE)){
         throw new ElementNotExistInDBException("Tecnico ingresado no existe");
      }
      else if (filters.containsKey("clientId")
          && !clientRepository.existsByIdAndUserState(Integer.parseInt(filters.get("clientId")), State.ACTIVE)){
         throw new ElementNotExistInDBException("Cliente ingresado no existe");
      }
   }

   @Override
   public ReviewDto findById(Integer id) throws ElementNotExistInDBException {
      Review review = reviewRepository.findById(id)
          .orElseThrow(() -> new ElementNotExistInDBException("Review con id "+id+" no existe."));
      return new ReviewDto(review);
   }

   @Override
   public ReviewDto save(ReviewDto reviewDto) throws ElementNotExistInDBException {
      Technical technical = technicalRepository.findById(reviewDto.getTechnicalId())
          .orElseThrow(() -> new ElementNotExistInDBException("Tecnico con id "+reviewDto.getTechnicalId()+" no encontrado"));
      Client client = clientRepository.findById(reviewDto.getClientId())
          .orElseThrow(() -> new ElementNotExistInDBException("Cliente con id "+reviewDto.getClientId()+" no encontrado"));

      Review review = reviewRepository.save(
          Review.builder()
              .technical(technical)
              .client(client)
              .numberStars(reviewDto.getNumberStars())
              .title(reviewDto.getTitle())
              .comment(reviewDto.getComment())
              .date(new Date())
              .hour(Time.valueOf(LocalTime.now()))
              .build()
      );
      return new ReviewDto(review);
   }
}
