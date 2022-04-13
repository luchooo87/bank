package com.example.bank.controller.impl;

import com.example.bank.controller.interfaces.IMovementWController;
import com.example.bank.dto.write.MovementWDto;
import com.example.bank.response.exception.BadRequestException;
import com.example.bank.response.exception.InternalServerException;
import com.example.bank.response.exception.NotFoundException;
import com.example.bank.response.success.ResponseCreated;
import com.example.bank.response.success.ResponseOk;
import com.example.bank.services.interfaces.IMovementWService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/movimientos")
public class MovementWController implements IMovementWController {

    @Autowired
    private IMovementWService _movementWService;

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping
    public ResponseEntity<?> create(@RequestBody MovementWDto movementWDto) {
        try {
            this._movementWService.create(movementWDto);
            return ResponseCreated.created();
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

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            this._movementWService.delete(id);
            return ResponseOk.ok();
        } catch (final NotFoundException exception) {
            log.error("Movement delete: ", exception);
            throw new NotFoundException(exception.getMessage());
        } catch (final BadRequestException exception) {
            log.error("Movement delete: ", exception);
            throw new BadRequestException(exception.getMessage());
        } catch (final Exception exception) {
            log.error("Movement delete: ", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }
}