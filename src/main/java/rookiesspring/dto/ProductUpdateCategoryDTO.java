/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 *
 * @author HP
 * @author Tamina
 */
@Data
public class ProductUpdateCategoryDTO {

    @Positive(message = "Product Id must be positive")
    long product_id;
    @NotNull(message = "Category Ids must not be null")
    long[] category_id;
}
