package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ClientDto;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.User;
import com.springtec.models.enums.State;
import com.springtec.models.repositories.ClientRepository;
import com.springtec.services.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientImplService implements IClientService {

    private final ClientRepository clientRepository;


    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client findById(Integer id) {
        return null;
    }

    @Override
    public ClientDto findByUser(User user) {
        Client client = clientRepository.findByUser(user);
        return ClientDto.builder()
            .id(client.getId())
            .name(client.getName())
            .lastname(client.getLastname())
            .motherLastname(client.getMotherLastname())
            .dni(client.getDni())
            .birthDate(client.getBirthDate())
            .user(client.getUser())
            .build();
    }

    @Override
    public void delete(Client client) {

    }



}
