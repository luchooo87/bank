package com.example.bank.controller.interfaces;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

public interface IMovementRController {

    /**
     * @param accountNumber
     * @param startDate
     * @param endDate
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> getAll(@RequestParam(value = "account") String accountNumber,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate);

}
