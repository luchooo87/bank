package com.example.bank.controller.interfaces;

import com.example.bank.dto.write.ClientWDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IClientWController {

    /**
     * @param clientWDto
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> create(@RequestBody ClientWDto clientWDto);

    /**
     * @param clientWDto
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> update(@RequestBody ClientWDto clientWDto);

    /**
     * @param id
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> delete(@PathVariable Integer id);

}
