package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TicketSupportDTO;
import com.springtec.models.dto.UserDto;
import com.springtec.models.entity.TicketSupport;
import com.springtec.models.entity.User;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.TicketSupportRepository;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.ITicketSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketSupportImplService implements ITicketSupportService {

   private final TicketSupportRepository TicketRepository;
    private final UserRepository userRepository;
    @Override
    public List<TicketSupport> findAll() {
        return TicketRepository.findAll();
    }

    @Override
    public TicketSupportDTO save(TicketSupportDTO ticketSupport) throws ElementNotExistInDBException {
        boolean user = userRepository.existsById(ticketSupport.getUserId());
        if(!user){
            throw new ElementNotExistInDBException("El usuario no existe");
        }

        TicketSupport ticket = TicketRepository.save(
                TicketSupport.builder()
                        .user(User.builder().id(ticketSupport.getUserId()).build())
                        .issue(ticketSupport.getIssue())
                        .description(ticketSupport.getDescription())
                        .date(ticketSupport.getDate())
                        .state(State.ACTIVE)
                        .build()
        );

        return TicketSupportDTO.builder()
                .id(ticket.getId())
                .userId(ticket.getUser().getId())
                .issue(ticket.getIssue())
                .description(ticketSupport.getDescription())
                .date(ticket.getDate())
                .state(ticket.getState())
                .build();
    }


}
