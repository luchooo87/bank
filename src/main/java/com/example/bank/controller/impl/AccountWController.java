package com.example.bank.controller.impl;

import com.example.bank.controller.interfaces.IAccountWController;
import com.example.bank.dto.write.AccountWDto;
import com.example.bank.response.exception.BadRequestException;
import com.example.bank.response.exception.InternalServerException;
import com.example.bank.response.exception.NotFoundException;
import com.example.bank.response.success.ResponseCreated;
import com.example.bank.response.success.ResponseOk;
import com.example.bank.services.interfaces.IAccountWService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/cuentas")
public class AccountWController implements IAccountWController {

    @Autowired
    private IAccountWService _accountWService;

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccountWDto accountWDto) {
        try {
            this._accountWService.create(accountWDto);
            return ResponseCreated.created();
        } catch (final BadRequestException exception) {
            log.error("Account save: ", exception);
            throw new BadRequestException(exception.getMessage());
        } catch (final NotFoundException exception) {
            log.error("Account save: ", exception);
            throw new NotFoundException(exception.getMessage());
        } catch (final Exception exception) {
            log.error("Account save: ", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PutMapping
    public ResponseEntity<?> update(@RequestBody AccountWDto accountWDto) {
        try {
            this._accountWService.update(accountWDto);
            return ResponseOk.ok();
        } catch (final NotFoundException exception) {
            log.error("Account update: ", exception);
            throw new NotFoundException(exception.getMessage());
        } catch (final BadRequestException exception) {
            log.error("Account update: ", exception);
            throw new BadRequestException(exception.getMessage());
        } catch (final Exception exception) {
            log.error("Account update: ", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            this._accountWService.delete(id);
            return ResponseOk.ok();
        } catch (final NotFoundException exception) {
            log.error("Account delete: ", exception);
            throw new NotFoundException(exception.getMessage());
        } catch (final BadRequestException exception) {
            log.error("Account delete: ", exception);
            throw new BadRequestException(exception.getMessage());
        } catch (final Exception exception) {
            log.error("Account delete: ", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }
}