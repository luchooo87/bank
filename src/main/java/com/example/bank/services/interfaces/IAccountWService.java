package com.example.bank.services.interfaces;

import com.example.bank.dto.write.AccountWDto;
import com.example.bank.model.Account;

public interface IAccountWService {

    /**
     * @param accountWDto
     * @return Account
     */
    Account create(AccountWDto accountWDto);

    /**
     * @param accountWDto
     * @return Account
     */
    Account update(AccountWDto accountWDto);

    /**
     * @param id
     * @return Account
     */
    Account delete(Integer id);

}
