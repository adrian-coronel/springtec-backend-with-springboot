package com.springtec.models.repositories;

import com.springtec.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //Aqui JPA entiende que queremos buscar por EMAIL
    Optional<User> findByEmail(String email) ;
}
