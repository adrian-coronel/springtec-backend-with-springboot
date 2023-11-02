package com.springtec.controllers;

import com.springtec.models.entity.Client;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @GetMapping("clients")
    public ResponseEntity<?> showAll(){
        List<Client> clients = clientService.findAll();
        if (clients.isEmpty()) {
            return new ResponseEntity<>(
                MessageResponse.builder()
                    .message("No hay registros.")
                    .body(null)
                    .build()
                , HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
            MessageResponse.builder()
                .message("")
                .body(clients)
                .build()
            , HttpStatus.OK
        );
    }
}