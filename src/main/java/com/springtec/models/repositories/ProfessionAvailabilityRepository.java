package com.springtec.models.repositories;

import com.springtec.models.entity.ProfessionAvailability;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProfessionAvailabilityRepository extends JpaRepository<ProfessionAvailability, Integer> {

   Set<ProfessionAvailability> findAllByTechnicalId(Integer technical_id);

   Set<ProfessionAvailability> findAllByTechnicalIdAndProfessionId(Integer technical_id,Integer profession_id);
   Optional<ProfessionAvailability> findById(Integer id);

   boolean existsByTechnicalIdAndAvailabilityIdAndProfessionIdAndExperienceId(
       Integer technical_id, Integer availability_id, Integer profession_id, Integer experience_id);
   boolean existsById(Integer id);

   @Query(nativeQuery = true, value = "CALL ExistsAvailabilityProfessionExperience(:pAvailabilityId, :pProfessionId, :pExperienceId);")
    String existsAvailabilityAndProfessionAndExperience(
       @Param("pAvailabilityId") int availabilityId,
       @Param("pProfessionId") int professionId,
       @Param("pExperienceId") int experienceId
   );

}
