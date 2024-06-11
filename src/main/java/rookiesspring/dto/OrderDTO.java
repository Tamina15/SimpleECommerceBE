/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import rookiesspring.dto.update.Product_Amount;

/**
 *
 * @author HP
 */
public record OrderDTO(
        @NotEmpty(message = "Products cannot be empty")
        Product_Amount[] products) {

}
