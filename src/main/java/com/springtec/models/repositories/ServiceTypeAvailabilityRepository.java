package com.springtec.models.repositories;

import com.springtec.models.entity.ServiceTypeAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeAvailabilityRepository extends JpaRepository<ServiceTypeAvailability, Integer> {
}
