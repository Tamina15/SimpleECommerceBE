/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

/**
 *
 * @author HP
 * @author Tamina
 */
public record RemoveUserDTO(
        @Positive(message = "User Id must not be empty")
        long user_id,
        boolean hard) {

}
