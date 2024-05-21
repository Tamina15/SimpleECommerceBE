/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import rookiesspring.dto.CartDTO;
import rookiesspring.dto.response.CartResponseDTO;
import rookiesspring.model.Cart;

/**
 *
 * @author HP
 * @author Tamina
 */

@Service
public class CartMapper implements BaseMapper<Cart, CartDTO, CartResponseDTO> {

    @Override
    public Cart toEntity(CartDTO dto) {
        return new Cart();
    }

    @Override
    public CartResponseDTO ToResponseDTO(Cart e) {
        return new CartResponseDTO(e.getUser().getId(), e.getProduct().getId(), e.getAmount());
    }

    @Override
    public List<CartResponseDTO> ToResponseDTOList(List<Cart> e) {
        List<CartResponseDTO> list = new ArrayList<>();
        for (Cart c : e) {
            list.add(ToResponseDTO(c));
        }
        return list;
    }

}
