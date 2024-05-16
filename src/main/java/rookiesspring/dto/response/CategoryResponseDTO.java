/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto.response;

import java.util.List;
import rookiesspring.dto.response.custom.ProductResponseDTOShort;

/**
 *
 * @author HP
 * @author Tamina
 */
public record CategoryResponseDTO(long id, String name, String description, List<ProductResponseDTOShort> products) {

}
