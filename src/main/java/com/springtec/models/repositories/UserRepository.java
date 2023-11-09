package com.springtec.models.repositories;

import com.springtec.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //Aqui JPA entiende que queremos buscar por EMAIL
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    //User update(User user);

    boolean existsById(Integer id);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.state = :newState WHERE u.id = :userId")
    void updateStateById(@Param("newState") char newState, @Param("userId") Integer userId);
}
