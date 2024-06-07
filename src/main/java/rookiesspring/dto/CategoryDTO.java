/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author HP
 */
public record CategoryDTO(
        @NotNull(message = "Category Name must not be null")
        String name, 
        @NotNull(message = "Category Description must not be null")
        String description, long[] product_id) {

}
