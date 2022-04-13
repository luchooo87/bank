package com.example.bank.services.impl;

import com.example.bank.model.Account;
import com.example.bank.repository.IAccountRepository;
import com.example.bank.response.exception.NotFoundException;
import com.example.bank.services.interfaces.IAccountRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountRService implements IAccountRService {

    @Autowired
    private IAccountRepository _accountRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getById(Integer id) {
        Optional<Account> account = this._accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new NotFoundException("Cuenta no existe");
        }

        return account.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Account> getAll() {
        return this._accountRepository.findAllByStatusEquals(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getByAccountNumber(String accountNumber) {
        Optional<Account> account = this._accountRepository.findByNumber(accountNumber);
        if (account.isEmpty()) {
            throw new NotFoundException("Cuenta no existe");
        }

        return account.get();
    }
}
