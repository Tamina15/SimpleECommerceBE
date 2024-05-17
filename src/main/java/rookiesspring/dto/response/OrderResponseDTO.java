/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto.response;

import java.time.LocalDateTime;
import rookiesspring.dto.response.custom.UserResponseDTOShort;

/**
 *
 * @author HP
 */
public record OrderResponseDTO(long id, String totalPrice, LocalDateTime createdDate, UserResponseDTOShort user) {

}
