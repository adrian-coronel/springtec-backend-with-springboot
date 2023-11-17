package com.springtec.models.repositories;

import com.springtec.models.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {

   List<Services> findAllByTechnicalIdAndCategoryServiceIdAndProfessionId(Integer technical_id, Integer categoryService_id, Integer profession_id);

}
