/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author HP
 * @author Tamina
 */
public record CartDTO(
        @NotNull
        long product_id,
        @Min(value = 1, message = "Minimal value is 1")
        int amount) {

}
