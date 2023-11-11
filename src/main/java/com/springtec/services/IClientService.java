package com.springtec.services;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ClientDto;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.User;

import java.util.List;

public interface IClientService {
    List<Client> findAll();


    //save sirve para guardar como tambien para (actualizar siempre y cuando se le envie el Id)
    ClientDto update(ClientDto clientDto,Integer id) throws ElementNotExistInDBException;
    Client findById(Integer id) throws ElementNotExistInDBException;
    ClientDto findByUser(User user);

    void delete(Client client);
}
