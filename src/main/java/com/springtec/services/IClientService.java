package com.springtec.services;

import com.springtec.models.dto.ClientDto;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.User;

import java.util.List;

public interface IClientService {
    List<Client> findAll();
    // Cuando se envia el ID en save() CrudRepository reconoce que
    // se quiere actualizar y lo hace por detrás
    Client save(Client client);
    Client findById(Integer id);
    ClientDto findByUser(User user);
    void delete(Client client);
}
