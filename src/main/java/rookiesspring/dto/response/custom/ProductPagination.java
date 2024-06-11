/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto.response.custom;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import rookiesspring.dto.response.ProductResponseDTO;

/**
 *
 * @author HP
 * @author Tamina
 */
@Data
@AllArgsConstructor
public class ProductPagination {

    List<ProductResponseDTO> products;
    long count;
}
