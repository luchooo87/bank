package com.example.bank.controller.impl;

import com.example.bank.controller.interfaces.IClientWController;
import com.example.bank.dto.write.ClientWDto;
import com.example.bank.response.exception.BadRequestException;
import com.example.bank.response.exception.InternalServerException;
import com.example.bank.response.exception.NotFoundException;
import com.example.bank.response.success.ResponseCreated;
import com.example.bank.response.success.ResponseOk;
import com.example.bank.services.interfaces.IClientWService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/clientes")
public class ClientWController implements IClientWController {

    @Autowired
    private IClientWService _clientWService;

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClientWDto clientWDto) {
        try {
            this._clientWService.create(clientWDto);
            return ResponseCreated.created();
        } catch (final BadRequestException exception) {
            log.error("Client save: ", exception);
            throw new BadRequestException(exception.getMessage());
        } catch (final Exception exception) {
            log.error("Client save: ", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ClientWDto clientWDto) {
        try {
            this._clientWService.update(clientWDto);
            return ResponseOk.ok();
        } catch (final NotFoundException exception) {
            log.error("Client update: ", exception);
            throw new NotFoundException(exception.getMessage());
        } catch (final BadRequestException exception) {
            log.error("Client update: ", exception);
            throw new BadRequestException(exception.getMessage());
        } catch (final Exception exception) {
            log.error("Client update: ", exception);
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
            this._clientWService.delete(id);
            return ResponseOk.ok();
        } catch (final NotFoundException exception) {
            log.error("Client delete: ", exception);
            throw new NotFoundException(exception.getMessage());
        } catch (final BadRequestException exception) {
            log.error("Client delete: ", exception);
            throw new BadRequestException(exception.getMessage());
        } catch (final Exception exception) {
            log.error("Client delete: ", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }
}