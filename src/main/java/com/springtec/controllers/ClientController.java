package com.springtec.controllers;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ClientDto;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.entity.Client;
import com.springtec.models.payload.MessageResponse;
import com.springtec.services.IClientService;
import lombok.RequiredArgsConstructor;

import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("client/{id}")
    public ResponseEntity<?> showOne(@PathVariable Integer id){
        try {
            Client client = clientService.findById(id);
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("")
                            .body(client)
                            .build()
                    , HttpStatus.OK
            );

        } catch (ElementNotExistInDBException existInDBException) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(existInDBException.getMessage())
                            .body(null)
                            .build()
                    , HttpStatus.OK
            );
        }

    }


    @PutMapping("client/{id}")
    public ResponseEntity<?> updateClient(@RequestBody ClientDto clientDto,@PathVariable Integer id) {

        try{
            ClientDto updatingClient = clientService.update(clientDto,id);
            return  new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("Cliente Actualizado")
                            .body(updatingClient)
                            .build()
                    , HttpStatus.OK
            );
        }catch (ElementNotExistInDBException e){
            return  new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(e.getMessage())
                            .body(null)
                            .build()
                    , HttpStatus.OK
            );
        }


    }



}
