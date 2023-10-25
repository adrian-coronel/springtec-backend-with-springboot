package com.springtec.models.repositories;

import com.springtec.models.entity.Technical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalRepository extends JpaRepository<Technical, Integer> {
}
