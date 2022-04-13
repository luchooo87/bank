package com.example.bank.services.impl;

import com.example.bank.dto.read.MovementRDto;
import com.example.bank.model.AccountMovement;
import com.example.bank.repository.IAccountMovementsRepository;
import com.example.bank.response.exception.NotFoundException;
import com.example.bank.services.interfaces.IMovementRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovementRService implements IMovementRService {

    @Autowired
    private IAccountMovementsRepository _accountMovementsRepository;

    @Override
    public AccountMovement getById(Integer id) {
        Optional<AccountMovement> movement = this._accountMovementsRepository.findById(id);
        if (movement.isEmpty()) {
            throw new NotFoundException("Movimiento no existe");
        }

        return movement.get();
    }

    @Override
    public List<AccountMovement> getByRangeDate(Date startDate, Date endDate, String accountNumber) {
        List<AccountMovement> movements = this._accountMovementsRepository.findByMovementDateBetweenAndAccount_Number(
                this.getStartDate(startDate), this.getEndDate(endDate), accountNumber);
        return movements;
    }

    @Override
    public List<AccountMovement> getByAccountNumber(String accountNumber) {
        return this._accountMovementsRepository.findByAccount_Number(accountNumber);
    }

    @Override
    public List<MovementRDto> getReport(Date startDate, Date endDate, String accountNumber) {
        return this._accountMovementsRepository.generateReport(this.getStartDate(startDate), this.getEndDate(endDate), accountNumber);
    }

    private Date getStartDate(Date date) {
        LocalDate localEndDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime startOfDay = localEndDate.atTime(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date getEndDate(Date date) {
        LocalDate localEndDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime endOfDay = localEndDate.atTime(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
}
