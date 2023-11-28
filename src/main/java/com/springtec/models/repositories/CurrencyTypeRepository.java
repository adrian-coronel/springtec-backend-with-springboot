package com.springtec.models.repositories;

import com.springtec.models.entity.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyTypeRepository extends JpaRepository<CurrencyType, Integer> {
}
