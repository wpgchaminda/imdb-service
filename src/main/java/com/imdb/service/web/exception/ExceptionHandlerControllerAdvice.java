package com.imdb.service.web.exception;

import com.imdb.service.web.dto.ErrorResponse;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ExceptionHandlerControllerAdvice which handles following exception types.
 * ResourceNotFoundException
 * BadRequestException
 * Exception
 */
@Log
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

  @Autowired
  private MessageSource messageSource;

  /**
   * ResourceNotFoundException handler.
   *
   * @param e ResourceNotFoundException
   * @return ErrorResponse
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public @ResponseBody
  ErrorResponse handleResourceNotFound(final ResourceNotFoundException e) {
    log.log(Level.INFO, "Not Found: " + e.getMessage());
    return new ErrorResponse("404", "Not Found",
        getMessage(e.getMessage(), e.getArgs()));
  }

  /**
   * BadRequestException handler.
   *
   * @param e BadRequestException
   * @return ErrorResponse
   */
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody
  ErrorResponse handleBadRequest(final BadRequestException e) {
    log.log(Level.SEVERE, "Bad Request: " + e.getMessage());
    return new ErrorResponse("400", "Bad Request",
        getMessage(e.getMessage(), e.getArgs()));
  }

  /**
   * Exception handler.
   *
   * @param e Exception
   * @return ErrorResponse
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody
  ErrorResponse handleException(final Exception e) {
    log.log(Level.SEVERE, "Server Error: " + e.getMessage());
    return new ErrorResponse("500", "Internal Server Error",
        getMessage("imdb.service.error.service.response.server.error", null));
  }

  private String getMessage(String msg, String[] args) {
    try {
      return messageSource.getMessage(msg, args, null);
    } catch (Exception e) {
      return msg;
    }
  }
}
