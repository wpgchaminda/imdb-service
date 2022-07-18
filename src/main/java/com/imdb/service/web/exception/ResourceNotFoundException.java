package com.imdb.service.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  private String[] args;

  public ResourceNotFoundException() {
    super();
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message,String[] args) {
    super(message);
    this.args=args;
  }

  public ResourceNotFoundException(String message, Throwable cause, String[] args) {
    super(message, cause);
    this.args=args;
  }

  public ResourceNotFoundException(Throwable cause) {
    super(cause);
  }

  protected ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace, String[] args) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.args=args;
  }

  public String[] getArgs() {
    return args;
  }

  public void setArgs(String[] args) {
    this.args = args;
  }
}
