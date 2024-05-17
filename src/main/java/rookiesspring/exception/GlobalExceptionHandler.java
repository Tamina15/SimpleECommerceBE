/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author HP
 * @author Tamina
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        var error = ErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(e.getMessage() != null ? e.getMessage() : "Internal Server Error").build();
        System.out.println(error);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class})
    protected ResponseEntity handleResourceNotFoundException(RuntimeException exception) {
        var error = ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value()).message(exception.getMessage() != null ? exception.getMessage() : "Resource Not Found").build();
        System.out.println(error);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity handleEntityExistsException(RuntimeException e) {
        var error = ErrorResponse.builder().code(HttpStatus.CONFLICT.value()).message(e.getMessage() != null ? e.getMessage() : "Already Exist").build();
        System.out.println(error);
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler({UnsupportedOperationException.class})
    public ResponseEntity handleUnsupportedOperationException(RuntimeException e) {
        var error = ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value()).message(e.getMessage() != null ? e.getMessage() : "Function Not Yet Implemented. Please Comeback Later").build();
        System.out.println(error);
        return ResponseEntity.status(error.getCode()).body(error);
    }

}
