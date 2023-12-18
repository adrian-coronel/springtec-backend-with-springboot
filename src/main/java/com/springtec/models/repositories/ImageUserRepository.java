package com.springtec.models.repositories;

import com.springtec.models.entity.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageUserRepository  extends JpaRepository<ImageUser, Integer> {
   ImageUser findByUserId(Integer userId);
   boolean existsByUserId(Integer userId);
}
