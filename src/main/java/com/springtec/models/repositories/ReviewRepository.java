package com.springtec.models.repositories;

import com.springtec.models.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

   List<Review> findAllByTechnicalId(Integer technical_id);
   List<Review> findAllByClientId(Integer client_id);

}
