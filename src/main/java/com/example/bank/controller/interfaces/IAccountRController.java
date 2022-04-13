package com.example.bank.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IAccountRController {

    /**
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> getAll();

    /**
     * @param id
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> getById(@PathVariable Integer id);

}
