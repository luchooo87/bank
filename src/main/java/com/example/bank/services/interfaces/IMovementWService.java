package com.example.bank.services.interfaces;

import com.example.bank.dto.write.MovementWDto;
import com.example.bank.model.AccountMovement;

public interface IMovementWService {

    /**
     * @param movementWDto
     * @return AccountMovement
     */
    AccountMovement create(MovementWDto movementWDto);

    /**
     * @param movementWDto
     * @return AccountMovement
     */
    AccountMovement createFirstMovement(MovementWDto movementWDto);

    /**
     * @param id
     * @return AccountMovement
     */
    AccountMovement delete(Integer id);

}
