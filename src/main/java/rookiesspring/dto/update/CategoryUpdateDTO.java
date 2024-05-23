/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto.update;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author HP
 */
public record CategoryUpdateDTO(@NotNull(message = "Category Id must not be null")long id, String name, String description) {

}
