package com.example.bank.response.success;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseOk {

  public static <T> ResponseEntity<?> ok(T body) {
    return new ResponseEntity<T>(body, HttpStatus.OK);
  }

  public static <T> ResponseEntity<?> ok() {
    return new ResponseEntity<T>(HttpStatus.OK);
  }

}
