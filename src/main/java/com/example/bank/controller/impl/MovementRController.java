package com.example.bank.controller.impl;

import com.example.bank.controller.interfaces.IMovementRController;
import com.example.bank.response.exception.BadRequestException;
import com.example.bank.response.exception.InternalServerException;
import com.example.bank.response.exception.NotFoundException;
import com.example.bank.response.success.ResponseOk;
import com.example.bank.services.interfaces.IMovementRService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("api/movimientos")
public class MovementRController implements IMovementRController {

    @Autowired
    private IMovementRService _movementRService;

    /**
     * {@inheritDoc}
     */
    @GetMapping
    @Override
    public ResponseEntity<?> getAll(@RequestParam(value = "account") String accountNumber,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            return ResponseOk.ok(this._movementRService.getReport(startDate, endDate, accountNumber));
        } catch (final BadRequestException exception) {
            log.error("Movement save: ", exception);
            throw new BadRequestException(exception.getMessage());
        } catch (final NotFoundException exception) {
            log.error("Movement save: ", exception);
            throw new NotFoundException(exception.getMessage());
        } catch (final Exception exception) {
            log.error("Movement save: ", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }
}