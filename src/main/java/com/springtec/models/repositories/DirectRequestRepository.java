package com.springtec.models.repositories;

import com.springtec.models.entity.DirectRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectRequestRepository extends JpaRepository<DirectRequest, Integer> {

   List<DirectRequest> findByProfessionAvailabilityTechnicalIdAndState(Integer professionAvailability_technical_id, char state);

}
