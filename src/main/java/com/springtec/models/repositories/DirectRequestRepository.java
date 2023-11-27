package com.springtec.models.repositories;

import com.springtec.models.entity.DirectRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectRequestRepository extends JpaRepository<DirectRequest, Integer> {

   DirectRequest findByProfessionAvailabilityTechnicalId(Integer professionAvailability_technical_id);

}
