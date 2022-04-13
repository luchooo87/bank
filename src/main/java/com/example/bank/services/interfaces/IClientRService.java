package com.example.bank.services.interfaces;

import com.example.bank.model.Client;

import java.util.List;

public interface IClientRService {

    /**
     * @param id
     * @return Client
     */
    Client getById(Integer id);

    /**
     * @return List<Client>
     */
    List<Client> getAll();

    /**
     * @param fullName
     * @return Client
     */
    Client getByFullName(String fullName);

}
