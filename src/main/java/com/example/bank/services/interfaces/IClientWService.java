package com.example.bank.services.interfaces;

import com.example.bank.dto.write.ClientWDto;
import com.example.bank.model.Client;

public interface IClientWService {

    /**
     * @param clientWDto
     * @return Client
     */
    Client create(ClientWDto clientWDto);

    /**
     * @param clientWDto
     * @return Client
     */
    Client update(ClientWDto clientWDto);

    /**
     * @param id
     * @return Client
     */
    Client delete(Integer id);

}
