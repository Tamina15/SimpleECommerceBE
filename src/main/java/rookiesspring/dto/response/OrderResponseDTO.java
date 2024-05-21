/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto.response;

import java.time.LocalDateTime;
import java.util.Set;
import rookiesspring.dto.response.custom.UserResponseDTOShort;
import rookiesspring.dto.update.Product_Amount;

/**
 *
 * @author HP
 */
public record OrderResponseDTO(long id, String totalPrice, LocalDateTime createdDate, UserResponseDTOShort user, Set<Product_Amount> products, boolean processed) {

}
