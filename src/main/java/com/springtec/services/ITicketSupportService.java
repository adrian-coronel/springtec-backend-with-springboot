package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TicketSupportDTO;
import com.springtec.models.entity.TicketSupport;

import java.util.List;

public interface ITicketSupportService {
    List<TicketSupport> findAll();

    TicketSupportDTO save(TicketSupportDTO ticketSupport) throws ElementNotExistInDBException;
}
