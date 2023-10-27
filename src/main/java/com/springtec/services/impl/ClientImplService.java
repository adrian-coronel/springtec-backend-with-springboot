package com.springtec.services.impl;

import com.springtec.models.entity.Client;
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
    public void delete(Client client) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

}
