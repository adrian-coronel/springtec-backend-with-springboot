package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.models.dto.ClientDto;
import com.springtec.models.entity.Client;
import com.springtec.models.entity.User;
import com.springtec.models.repositories.ClientRepository;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientImplService implements IClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public ClientDto update(ClientDto clientDto,Integer id) throws ElementNotExistInDBException {
          Client client=clientRepository.findById(id)
                  .orElseThrow(()->new ElementNotExistInDBException("El cliente no existe"));


          client.setName(clientDto.getName());
          client.setLastname(clientDto.getLastname());
          client.setMotherLastname(clientDto.getMotherLastname());
          client.setBirthDate(clientDto.getBirthDate());

          Client clientUpdate = clientRepository.save(client);
          return new ClientDto(clientUpdate);
    }

    @Override
    public Client findById(Integer id) throws ElementNotExistInDBException {
        return clientRepository.findById(id)
                .orElseThrow(()->new ElementNotExistInDBException("El cliente con id :"+id+" no existe"));
    }

    @Override
    public void delete(Client client) {

    }



}
