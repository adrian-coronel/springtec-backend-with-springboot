package com.springtec.models.repositories;

import com.springtec.models.entity.DirectRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface DirectRequestRepository extends JpaRepository<DirectRequest, Integer> {

   List<DirectRequest> findAllByClientIdAndCreatedAtGreaterThanEqualAndStateDirectRequestId(Integer client_id, Timestamp createdAt, Integer stateDirectRequest_id);
   List<DirectRequest> findAllByClientIdAndStateDirectRequestId(Integer client_id, Integer stateDirectRequest_id);
   @Query(nativeQuery = true, value = "CALL findAllByTechnicalIdAndDistintStateId(:ptechnicalId,:pstateId);")
   List<DirectRequest> findAllByTechnicalIdAndDistintState( @Param("ptechnicalId") int technicalId, @Param("pstateId") int stateId);

   @Query(nativeQuery = true, value = "CALL findAllByTechnicalIdAndStateId(:ptechnicalId,:pstateId);")
   List<DirectRequest> findAllByTechnicalIdAndState( @Param("ptechnicalId") int technicalId, @Param("pstateId") int stateId);
}
