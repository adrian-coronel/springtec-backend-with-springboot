package com.springtec.models.repositories;

import com.springtec.models.entity.Availability;
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

   List<TechnicalProfession> findAllByProfessionIdAndTechnicalAvailabilityIdAndTechnicalUserState(Integer profession_id, Integer technical_availability_id, char technical_user_state);

   List<TechnicalProfession> findAllByProfessionIdAndTechnicalUserState(Integer profession_id, char technical_user_state);

   TechnicalProfession findByTechnicalIdAndProfessionId(Integer technical_id, Integer profession_id);

}
