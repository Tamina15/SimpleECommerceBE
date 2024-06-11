/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 *
 * @author HP
 * @author Tamina
 */
public record RateDTO(
        @NotNull(message = "Product Id cannot be null")
        long product_id,
        @Positive(message = ("Rate cannot be zero"))
        int rating, String comment) {

}
