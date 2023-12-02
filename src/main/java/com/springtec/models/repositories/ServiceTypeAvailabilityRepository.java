package com.springtec.models.repositories;

import com.springtec.models.entity.ServiceTypeAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTypeAvailabilityRepository extends JpaRepository<ServiceTypeAvailability, Integer> {

   List<ServiceTypeAvailability> findAllByProfessionAvailabilityTechnicalIdAndProfessionAvailabilityProfessionId(Integer professionAvailability_technical_id, Integer professionAvailability_profession_id);

}