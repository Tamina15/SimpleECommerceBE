/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.exception;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author HP
 * @author Tamina
 */
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        System.out.println("GlobalExceptionHandler Throwing Error");
            Logger.getLogger(GlobalExceptionHandler.class.getName()).log(Level.SEVERE, null, e);
        return ResponseEntity.status(500).body("Server Error");
    }
    
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> handleResourceNotFoundException(RuntimeException e) {
        Logger.getLogger(GlobalExceptionHandler.class.getName()).log(Level.SEVERE, null, e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource Not Found");
    }
    
    
    
    
    
}
