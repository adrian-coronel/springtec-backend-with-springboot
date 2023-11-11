package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ClientDto;
import com.springtec.models.entity.Client;

import java.util.List;

public interface IClientService {
    List<Client> findAll();


    //save sirve para guardar como tambien para (actualizar siempre y cuando se le envie el Id)
    ClientDto save(ClientDto clientDto);
    Client findById(Integer id) throws ElementNotExistInDBException;
    void delete(Client client);
}
