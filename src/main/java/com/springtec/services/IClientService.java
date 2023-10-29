package com.springtec.services;

import com.springtec.models.entity.Client;

import java.util.List;

public interface IClientService {
    List<Client> findAll();
    // Cuando se envia el ID en save() CrudRepository reconoce que
    // se quiere actualizar y lo hace por detrás
    Client save(Client client);
    Client findById(Integer id);
    void delete(Client client);
}
