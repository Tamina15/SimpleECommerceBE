/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto.update;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author HP
 */
public record ProductUpdateDTO(
        @NotNull(message = "Product Id cannot be null")
        long id,
        @NotNull(message = "Product Name cannot be null")
        String name,
        @Size(max = 255, message = "Maximum Desciption length is 255")
        String description,
        @Min(value = 1, message = "Minimal Price is 1")
        double price,
        @Min(value = 1, message = "Minimal Amount is 1")
        int amount,
        boolean feature) {

}
