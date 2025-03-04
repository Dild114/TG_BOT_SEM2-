package app.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceGlobalExceptionHandler {

  @ExceptionHandler(NoContentInRepositoryException.class)
  public void handleNoContentInRepositoryException(NoContentInRepositoryException e) {
    ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
  }

}
