/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rookiesspring.exception;

/**
 *
 * @author HP
 * @author Tamina
 */

public class EmptyCartException extends RuntimeException {

    public EmptyCartException(String message) {
        super(message);
    }

}
