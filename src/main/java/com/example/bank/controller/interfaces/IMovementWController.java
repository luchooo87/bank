package com.example.bank.controller.interfaces;

import com.example.bank.dto.write.MovementWDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IMovementWController {

    /**
     * @param movementWDto
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> create(@RequestBody MovementWDto movementWDto);

    /**
     * @param id
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> delete(@PathVariable Integer id);

}
