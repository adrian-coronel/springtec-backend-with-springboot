package com.springtec.models.repositories;

import com.springtec.models.dto.ClientDto;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsByDni(String dni);
    Client findByUser(User user);
    boolean existsByIdAndUserState(Integer clientId, char user_state);
}
