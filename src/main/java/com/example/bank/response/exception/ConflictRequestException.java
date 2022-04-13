package com.example.bank.response.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictRequestException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ConflictRequestException() {
    super();
  }

  public ConflictRequestException(String message) {
    super(message);
  }

  public ConflictRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConflictRequestException(Throwable cause) {
    super(cause);
  }

}
