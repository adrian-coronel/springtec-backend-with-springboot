package com.springtec.models.repositories;

import com.springtec.models.entity.TicketSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSupportRepository extends JpaRepository<TicketSupport,Integer> {



}
