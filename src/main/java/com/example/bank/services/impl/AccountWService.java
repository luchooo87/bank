package com.example.bank.services.impl;

import com.example.bank.dto.write.AccountWDto;
import com.example.bank.dto.write.MovementWDto;
import com.example.bank.model.Account;
import com.example.bank.model.Client;
import com.example.bank.repository.IAccountRepository;
import com.example.bank.response.exception.ConflictRequestException;
import com.example.bank.services.interfaces.IAccountRService;
import com.example.bank.services.interfaces.IAccountWService;
import com.example.bank.services.interfaces.IClientRService;
import com.example.bank.services.interfaces.IMovementWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountWService implements IAccountWService {

    @Autowired
    private IAccountRepository _accountRepository;

    @Autowired
    private IClientRService _clientRService;

    @Autowired
    private IAccountRService _accountRService;

    @Autowired
    private IMovementWService _movementWService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account create(AccountWDto accountWDto) {
        if (accountWDto.getId() != null) {
            throw new ConflictRequestException("Existe conflicto en los datos");
        }

        Client client = this._clientRService.getByFullName(accountWDto.getFullName());

        Account account = new Account();
        account.setAccountType(accountWDto.getAccountType());
        account.setInitialBalance(accountWDto.getInitialBalance());
        account.setNumber(accountWDto.getNumber());
        account.setClient(client);
        account.setStatus(true);
        this._accountRepository.save(account);
        MovementWDto movementWDto = MovementWDto.builder()
                .movementValue(account.getInitialBalance())
                .numberAccount(account.getNumber())
                .build();
        this._movementWService.createFirstMovement(movementWDto);
        return account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account update(AccountWDto accountWDto) {
        Account account = this._accountRService.getById(accountWDto.getId());
        //Client client = this._clientRService.getById(accountWDto.getClientId());
        Client client = this._clientRService.getByFullName(accountWDto.getFullName());
        account.setAccountType(accountWDto.getAccountType());
        account.setInitialBalance(accountWDto.getInitialBalance());
        account.setNumber(accountWDto.getNumber());
        account.setClient(client);
        this._accountRepository.save(account);
        return account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account delete(Integer id) {
        Account account = this._accountRService.getById(id);
        account.setStatus(false);
        this._accountRepository.save(account);
        return account;
    }
}
