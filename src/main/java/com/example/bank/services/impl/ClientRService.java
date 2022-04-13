package com.example.bank.services.impl;

import com.example.bank.model.Client;
import com.example.bank.repository.IClientRepository;
import com.example.bank.response.exception.NotFoundException;
import com.example.bank.services.interfaces.IClientRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientRService implements IClientRService {

    @Autowired
    private IClientRepository _clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client getById(Integer id) {
        Optional<Client> client = this._clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new NotFoundException("Cliente no existe");
        }

        return client.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> getAll() {
        return this._clientRepository.findAllByStatusEquals(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client getByFullName(String fullName) {
        Optional<Client> client = this._clientRepository.findByFullNameContainingIgnoreCase(fullName);
        if (client.isEmpty()) {
            throw new NotFoundException("Cliente no existe");
        }

        return client.get();
    }
}
