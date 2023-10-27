package com.springtec.models.repositories;

import com.springtec.models.entity.Profession;
import com.springtec.models.entity.Technical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Integer> {

    // DECLARO MI METODO PERSONALIZADO

    @Query("SELECT p FROM Profession p INNER JOIN TechnicalProfession tp ON tp.profession = p WHERE tp.technical = :technical")
    Set<Profession> findAllByTechnical(@Param("technical") Technical technical);


}
