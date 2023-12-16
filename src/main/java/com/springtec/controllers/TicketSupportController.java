package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.TicketSupportDTO;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.TicketSupport;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.ITicketSupportService;
import com.springtec.services.impl.TicketSupportImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class TicketSupportController {

    private final ITicketSupportService TicketSupportService;
    @CrossOrigin(origins = {"http://localhost:5173/"})
        @GetMapping("TicketSuport")
    public ResponseEntity<?> showAll(){
        List<TicketSupport> tickets = TicketSupportService.findAll();
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("No hay Tickets.")
                            .body(null)
                            .build()
                    , HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .body(tickets)
                        .build()
                , HttpStatus.OK
        );
    }
    @CrossOrigin(origins = {"http://localhost:5173/"})
    @PostMapping("TicketSuport")
    public ResponseEntity<?> createNew(@RequestBody TicketSupportDTO ticketSupport){
        try{
            TicketSupportDTO ticket = TicketSupportService.save(ticketSupport);
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("Ticket Enviado!")
                            .body(ticket)
                            .build()
                    ,HttpStatus.OK
            );
        } catch (ElementNotExistInDBException e) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(e.getMessage())
                            .body(e)
                            .build()
                    ,HttpStatus.OK
            );
        }


    }
}
