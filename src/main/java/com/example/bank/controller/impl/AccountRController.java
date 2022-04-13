package com.example.bank.controller.impl;

import com.example.bank.controller.interfaces.IAccountRController;
import com.example.bank.response.exception.InternalServerException;
import com.example.bank.response.exception.NotFoundException;
import com.example.bank.response.success.ResponseOk;
import com.example.bank.services.interfaces.IAccountRService;
import com.example.bank.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/cuentas")
public class AccountRController implements IAccountRController {

    @Autowired
    private IAccountRService _accountRService;

    /**
     * {@inheritDoc}
     */
    @GetMapping
    @JsonView({ Views.Read.class })
    @Override
    public ResponseEntity<?> getAll() {
        try {
            return ResponseOk.ok(this._accountRService.getAll());
        } catch (final Exception exception) {
            log.error("Account getAll: ", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @GetMapping("{id}")
    @JsonView({ Views.Read.class })
    @Override
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            return ResponseOk.ok(this._accountRService.getById(id));
        } catch (final NotFoundException exception) {
            log.error("Account getById: ", exception);
            throw new NotFoundException(exception.getMessage());
        } catch (final Exception exception) {
            log.error("Account getById: ", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }
}