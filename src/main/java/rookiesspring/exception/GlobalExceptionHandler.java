/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 * @author Tamina
 */
@ControllerAdvice
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
public class GlobalExceptionHandler {

    public ResponseEntity buildException(Integer errorCode, String message) {
        var error = ErrorResponse.builder().code(errorCode).message(message).build();
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity handleException(Exception e) {
        Logger.getLogger(GlobalExceptionHandler.class.getName()).log(Level.SEVERE, null, e);
        return buildException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage() != null ? e.getMessage() : "Internal Server Error");
    }

    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class})
    protected ResponseEntity handleResourceNotFoundException(RuntimeException e) {
        return buildException(HttpStatus.NOT_FOUND.value(), e.getMessage() != null ? e.getMessage() : "Resource Not Found");
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity handleEntityExistsException(EntityExistsException e) {
        return buildException(HttpStatus.CONFLICT.value(), e.getMessage() != null ? e.getMessage() : "Entity Already Exist");
    }

    @ExceptionHandler({NotEnoughProductException.class})
    public ResponseEntity handleNotEnoughProductException(NotEnoughProductException e) {
        return buildException(HttpStatus.CONFLICT.value(), e.getMessage() != null ? e.getStackTrace()[0].toString() : "Not Enough Product");
    }
    
    @ExceptionHandler({EmptyCartException.class})
    public ResponseEntity handleEmptyCartException(EmptyCartException e) {
        return buildException(HttpStatus.BAD_REQUEST.value(), e.getMessage() != null ? e.getStackTrace()[0].toString() : "Not Enough Product");
    }

    @ExceptionHandler({UnsupportedOperationException.class})
    public ResponseEntity handleUnsupportedOperationException(UnsupportedOperationException e) {
        return buildException(HttpStatus.NOT_IMPLEMENTED.value(), e.getMessage() != null ? e.getStackTrace()[0].toString() : "Function Not Yet Implemented. Please Comeback Later");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException e) {
        return buildException(HttpStatus.BAD_REQUEST.value(), e.getMessage() != null ? Util.UpperCaseFirstLetter(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()) : "Invalid Parameter");
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestExceptions(BadRequestException e) {
        return buildException(HttpStatus.BAD_REQUEST.value(), e.getMessage() != null ? Util.UpperCaseFirstLetter(e.getStackTrace()[0].toString()) : "Bad Request");
    }
    

}
