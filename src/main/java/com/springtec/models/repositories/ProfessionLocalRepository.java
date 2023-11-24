package com.springtec.models.repositories;

import com.springtec.models.entity.ProfessionAvailability;
import com.springtec.models.entity.ProfessionLocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionLocalRepository extends JpaRepository<ProfessionLocal, Integer> {
   boolean existsByProfessionAvailabilityId(Integer professionAvailability_id);
   ProfessionLocal findByProfessionAvailabilityId(Integer professionAvailability_id);
   void deleteByProfessionAvailabilityId(Integer professionAvailability_id);

}
