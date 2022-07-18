package com.imdb.service.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
  private String[] args;

  public BadRequestException() {
    super();
  }

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, String[] args) {
    super(message);
    this.args=args;
  }

  public BadRequestException(String message, Throwable cause, String[] args) {
    super(message, cause);
    this.args=args;
  }

  public BadRequestException(Throwable cause) {
    super(cause);
  }

  protected BadRequestException(String message, Throwable cause, boolean enableSuppression,
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
