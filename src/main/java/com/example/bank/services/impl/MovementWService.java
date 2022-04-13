package com.example.bank.services.impl;

import com.example.bank.dto.write.MovementWDto;
import com.example.bank.model.Account;
import com.example.bank.model.AccountMovement;
import com.example.bank.repository.IAccountMovementsRepository;
import com.example.bank.response.exception.BadRequestException;
import com.example.bank.services.interfaces.IAccountRService;
import com.example.bank.services.interfaces.IMovementRService;
import com.example.bank.services.interfaces.IMovementWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class MovementWService implements IMovementWService {

    @Value("${withdrawal.limit}")
    private String withdrawalLimit;

    @Autowired
    private IAccountMovementsRepository _accountMovementsRepository;

    @Autowired
    private IMovementRService _movementRService;

    @Autowired
    private IAccountRService _accountRService;

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountMovement create(MovementWDto movementWDto) {
        if (movementWDto.getMovementValue().negate().compareTo(new BigDecimal(this.withdrawalLimit)) >= 0) {
            throw new BadRequestException("Cupo diario Excedido");
        }

        Account account = this._accountRService.getByAccountNumber(movementWDto.getNumberAccount());
        List<AccountMovement> movementsAll = this._movementRService.getByAccountNumber(movementWDto.getNumberAccount());
        BigDecimal balance = movementsAll.stream()
                .map(AccountMovement::getMovementValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (movementWDto.getMovementValue().compareTo(BigDecimal.ZERO) < 0) {
            List<AccountMovement> movementsDaily = this._movementRService.getByRangeDate(new Date(), new Date(),
                    movementWDto.getNumberAccount());
            this.validateLimitDaily(movementsDaily, movementWDto.getMovementValue().negate());
            if (balance.compareTo(movementWDto.getMovementValue().negate()) < 0) {
                throw new BadRequestException("Fondos insuficientes para retiro");
            }
        }

        BigDecimal totalBalance = balance.add(movementWDto.getMovementValue());
        AccountMovement movement = new AccountMovement();
        movement.setMovementType(movementWDto.getTypeMovement());
        movement.setMovementValue(movementWDto.getMovementValue());
        movement.setBalance(totalBalance);
        movement.setMovementDate(new Timestamp(System.currentTimeMillis()));
        movement.setAccount(account);
        movement.setStatus(true);
        this._accountMovementsRepository.save(movement);
        return movement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountMovement createFirstMovement(MovementWDto movementWDto) {
        Account account = this._accountRService.getByAccountNumber(movementWDto.getNumberAccount());
        AccountMovement movement = new AccountMovement();
        movement.setMovementType("INICIAL");
        movement.setMovementValue(movementWDto.getMovementValue());
        movement.setBalance(movementWDto.getMovementValue());
        movement.setMovementDate(new Timestamp(System.currentTimeMillis()));
        movement.setAccount(account);
        movement.setStatus(true);
        this._accountMovementsRepository.save(movement);
        return movement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountMovement delete(Integer id) {
        AccountMovement movement = this._movementRService.getById(id);
        movement.setStatus(false);
        this._accountMovementsRepository.save(movement);
        return movement;
    }

    private void validateLimitDaily(List<AccountMovement> movements, BigDecimal currentValue) {
        BigDecimal limitDay = new BigDecimal(this.withdrawalLimit);
        BigDecimal totalWithdrawalLimit = movements.stream()
                .filter(accountMovement -> accountMovement.getMovementValue().compareTo(BigDecimal.ZERO) < 0)
                .map(AccountMovement::getMovementValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .negate();

        if (totalWithdrawalLimit.add(currentValue).compareTo(limitDay) >= 0) {
            throw new BadRequestException("Cupo diario Excedido");
        }
    }
}
