package com.example.bank.response.success;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseCreated {

  public static <T> ResponseEntity<?> created(T body) {
    return new ResponseEntity<T>(body, HttpStatus.CREATED);
  }

  public static <T> ResponseEntity<?> created() {
    return new ResponseEntity<T>(HttpStatus.CREATED);
  }

}
