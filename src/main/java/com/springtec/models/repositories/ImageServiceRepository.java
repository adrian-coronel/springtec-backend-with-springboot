package com.springtec.models.repositories;

import com.springtec.models.entity.ImageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageServiceRepository extends JpaRepository<ImageService, Integer> {
   ImageService findByServiceId(Integer serviceId);
}