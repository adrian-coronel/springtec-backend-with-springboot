package com.springtec.models.repositories;

import com.springtec.models.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {

   @Query(nativeQuery = true, value = "CALL findAllByTechnicalIdAndProfessionIdAndCategoryId(:pTechnicalId, :pProfessionId, :pCategoryId);")
   List<Services> findAllByTechnicalIdAndProfessionIdAndCategoryId(
       @Param("pTechnicalId") int availabilityId,
       @Param("pProfessionId") int professionId,
       @Param("pCategoryId") int experienceId
   );

}
