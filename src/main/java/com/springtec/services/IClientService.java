package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.entity.Client;

import java.util.List;

public interface IClientService {
    List<Client> findAll();

    Client save(Client client);
    Client findById(Integer id) throws ElementNotExistInDBException;
    void delete(Client client);
}
