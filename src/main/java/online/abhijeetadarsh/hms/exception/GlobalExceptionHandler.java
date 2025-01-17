package online.abhijeetadarsh.hms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
//  @ExceptionHandler(UnauthorizedException.class)
//  public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
//    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//        .body(new ErrorResponse(ex.getMessage()));
//  }
//
//  @ExceptionHandler(ValidationException.class)
//  public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//        .body(new ErrorResponse(ex.getMessage()));
//  }
}
