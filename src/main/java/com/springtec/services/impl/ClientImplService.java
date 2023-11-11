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
    public ClientDto save(ClientDto clientDto) {

        User user = userRepository.getById(clientDto.getUser());


          Client client= clientRepository.save(Client.builder()
                  .dni(clientDto.getDni())
                  .name(clientDto.getName())
                  .lastname(clientDto.getLastname())
                  .motherLastname(clientDto.getMotherLastname())
                  .birthDate(clientDto.getBirthDate())
                  .id(clientDto.getId())
                  .user(user)
                  .build());

          return ClientDto.builder()
                  .dni(client.getDni())
                  .name(client.getName())
                  .lastname(client.getLastname())
                  .motherLastname(client.getMotherLastname())
                  .birthDate(client.getBirthDate())
                  .id(client.getId())
                  .user(clientDto.getUser())
                  .build();
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
