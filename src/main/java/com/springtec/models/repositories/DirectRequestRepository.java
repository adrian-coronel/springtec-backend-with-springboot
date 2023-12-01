package com.springtec.models.repositories;

import com.springtec.models.entity.DirectRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectRequestRepository extends JpaRepository<DirectRequest, Integer> {


   @Query(nativeQuery = true, value = "CALL findAllByTechnicalIdAndDistintStateId(:ptechnicalId,:pstateId);")
   List<DirectRequest> findAllByTechnicalIdAndDistintState( @Param("ptechnicalId") int technicalId, @Param("pstateId") int stateId);

   @Query(nativeQuery = true, value = "CALL findAllByTechnicalIdAndStateId(:ptechnicalId,:pstateId);")
   List<DirectRequest> findAllByTechnicalIdAndState( @Param("ptechnicalId") int technicalId, @Param("pstateId") int stateId);
}
