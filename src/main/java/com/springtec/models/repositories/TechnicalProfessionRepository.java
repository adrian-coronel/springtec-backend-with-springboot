package com.springtec.models.repositories;

import com.springtec.models.entity.Profession;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.TechnicalProfession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TechnicalProfessionRepository
        extends JpaRepository<TechnicalProfession, Integer> {
   List<TechnicalProfession> findAllByTechnical(Technical technical);
}
