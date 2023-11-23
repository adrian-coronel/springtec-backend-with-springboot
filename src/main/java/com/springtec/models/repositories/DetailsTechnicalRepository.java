package com.springtec.models.repositories;

import com.springtec.models.entity.DetailsTechnical;
import com.springtec.models.entity.Technical;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsTechnicalRepository extends JpaRepository<DetailsTechnical, Integer> {

   List<DetailsTechnical> findAllByTechnicalIdAndState(Integer technical_id, char state);

   DetailsTechnical findByIdAndTechnicalId(Integer id, Integer technical_id);
   boolean existsById(@NonNull Integer id);

   @Query(nativeQuery = true, value = "CALL ExistsDetailsTechnical(:pAvailabilityId, :pProfessionId, :pExperienceId)")
   String existsDetailsTechnical(
       @Param("pAvailabilityId") int availabilityId,
       @Param("pProfessionId") int professionId,
       @Param("pExperienceId") int experienceId);

   @Procedure(procedureName = "UpdateDetailsTechnicalState")
   void updateDetailsTechnicalState(char new_state, Integer technical_id);

}
