package com.example.bank.services.interfaces;

import com.example.bank.model.Account;

import java.util.List;

public interface IAccountRService {

    /**
     * @param id
     * @return Account
     */
    Account getById(Integer id);

    /**
     * @return List<Account>
     */
    List<Account> getAll();

    /**
     * @param accountNumber
     * @return Account
     */
    Account getByAccountNumber(String accountNumber);

}
