package com.springtec.models.repositories;

import com.springtec.models.entity.Technical;
import com.springtec.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TechnicalRepository extends JpaRepository<Technical, Integer> {
    boolean existsByDniAndUserState(String dni, char user_state);
    boolean existsByIdAndUserState(Integer id, char user_state);
    List<Technical> findAllByUserState(char user_state);
    Technical findByIdAndUserState(Integer id, char user_state);

    Technical findByUser(User user);

}
