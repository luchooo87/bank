package com.example.bank.controller.interfaces;

import com.example.bank.dto.write.AccountWDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAccountWController {

    /**
     * @param accountWDto
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> create(@RequestBody AccountWDto accountWDto);

    /**
     * @param accountWDto
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> update(@RequestBody AccountWDto accountWDto);

    /**
     * @param id
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> delete(@PathVariable Integer id);

}
