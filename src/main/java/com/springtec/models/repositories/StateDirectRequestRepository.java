package com.springtec.models.repositories;

import com.springtec.models.entity.StateDirectRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDirectRequestRepository extends JpaRepository<StateDirectRequest, Integer> {
}
