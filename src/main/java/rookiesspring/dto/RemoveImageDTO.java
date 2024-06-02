/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

/**
 *
 * @author HP
 * @author Tamina
 */
public record RemoveImageDTO(
        @Positive(message = "Product Id must not  be 0")
        long product_id,
        @NotEmpty(message = "Image Ids must not be empty")
        long[] image_id, boolean hard) {

}
