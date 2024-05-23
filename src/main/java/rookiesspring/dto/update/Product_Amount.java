/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto.update;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

/**
 *
 * @author HP
 */
public record Product_Amount(
        @NotNull(message = "Product cannot be null")
        long product_id, 
        @Min(value = 1, message = "Minimal amount is 1")
        int amount) {

}
