/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto.response;

import java.util.Set;
import rookiesspring.dto.response.custom.CategoryResponseDTOShort;
import rookiesspring.model.Image;

/**
 *
 * @author HP
 */
public record ProductResponseDTO(long id, String name, String description, double price, int amount, String rating, boolean feature, Set<CategoryResponseDTOShort> category, Set<Image> image) {

}
