package com.example.bank.services.impl;

import com.example.bank.dto.write.ClientWDto;
import com.example.bank.model.Client;
import com.example.bank.repository.IClientRepository;
import com.example.bank.response.exception.ConflictRequestException;
import com.example.bank.services.interfaces.IClientRService;
import com.example.bank.services.interfaces.IClientWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientWService implements IClientWService {

    @Autowired
    private IClientRepository _clientRepository;

    @Autowired
    private IClientRService _clientRService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Client create(ClientWDto clientWDto) {
        if (clientWDto.getId() != null) {
            throw new ConflictRequestException("Existe conflicto en los datos");
        }

        Client client = new Client();
        client.setFullName(clientWDto.getFullName());
        client.setPhone(clientWDto.getPhone());
        client.setAddress(clientWDto.getAddress());
        client.setPassword(clientWDto.getPassword());
        client.setAge(clientWDto.getAge());
        client.setIdentification(clientWDto.getIdentification());
        client.setStatus(true);
        this._clientRepository.save(client);
        return client;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Client update(ClientWDto clientWDto) {
        Client client = this._clientRService.getById(clientWDto.getId());
        client.setFullName(clientWDto.getFullName());
        client.setPhone(clientWDto.getPhone());
        client.setAddress(clientWDto.getAddress());
        client.setPassword(clientWDto.getPassword());
        client.setAge(clientWDto.getAge());
        client.setIdentification(clientWDto.getIdentification());
        this._clientRepository.save(client);
        return client;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Client delete(Integer id) {
        Client client = this._clientRService.getById(id);
        client.setStatus(false);
        this._clientRepository.save(client);
        return client;
    }
}
