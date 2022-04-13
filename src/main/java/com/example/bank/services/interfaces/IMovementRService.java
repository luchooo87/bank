package com.example.bank.services.interfaces;

import com.example.bank.dto.read.MovementRDto;
import com.example.bank.model.AccountMovement;

import java.util.Date;
import java.util.List;

public interface IMovementRService {

    /**
     * @param id
     * @return AccountMovement
     */
    AccountMovement getById(Integer id);

    /**
     * @param startDate
     * @param endDate
     * @param accountNumber
     * @return List<AccountMovement>
     */
    List<AccountMovement> getByRangeDate(Date startDate, Date endDate, String accountNumber);

    /**
     * @param accountNumber
     * @return List<AccountMovement>
     */
    List<AccountMovement> getByAccountNumber(String accountNumber);

    /**
     * @param startDate
     * @param endDate
     * @param accountNumber
     * @return List<MovementRDto>
     */
    List<MovementRDto> getReport(Date startDate, Date endDate, String accountNumber);

}
