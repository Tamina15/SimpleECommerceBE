/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author HP
 */
public record OrderUpdateDTO(
        @NotNull(message = "Order Id cannot be null")
        long order_id,
        @NotNull(message = "User Id cannot be null")
        long user_id, 
        @NotBlank(message = "Product cannot be empty")
        Product_Amount[] products) {

}
