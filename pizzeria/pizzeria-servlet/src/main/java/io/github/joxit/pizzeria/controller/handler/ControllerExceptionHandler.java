package io.github.joxit.pizzeria.controller.handler;

import io.github.joxit.pizzeria.exception.HandledException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Jones Magloire (@Joxit)
 * @since 2017-11-06
 */
@ControllerAdvice
public class ControllerExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler(HandledException.class)
  public ResponseEntity<JSONError> handledException(HandledException e) {
    LOGGER.warn(e.getMessage());
    return new ResponseEntity<>(new JSONError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  private static class JSONError {
    private int status;
    private String message;

    private JSONError(HttpStatus httpStatus, String message) {
      this.status = httpStatus.value();
      this.message = message;
    }

    public int getStatus() {
      return status;
    }

    public String getMessage() {
      return message;
    }
  }
}
