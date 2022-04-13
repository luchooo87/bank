package com.example.bank.response.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnautorizedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UnautorizedException() {
    super();
  }

  public UnautorizedException(String message) {
    super(message);
  }

  public UnautorizedException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnautorizedException(Throwable cause) {
    super(cause);
  }

}
