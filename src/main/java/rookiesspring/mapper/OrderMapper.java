/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import rookiesspring.dto.OrderDTO;
import rookiesspring.dto.response.OrderResponseDTO;
import rookiesspring.dto.update.Product_Amount;
import rookiesspring.model.Order;
import rookiesspring.model.composite_model.Order_Detail;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class OrderMapper implements BaseMapper<Order, OrderDTO, OrderResponseDTO> {

    UserMapper userMapper;
    ProductMapper productMapper;

    public OrderMapper(UserMapper userMapper, ProductMapper productMapper) {
        this.userMapper = userMapper;
        this.productMapper = productMapper;
    }

    @Override
    public Order toEntity(OrderDTO dto) {
        Order o = new Order();
        return o;
    }

    @Override
    public OrderResponseDTO ToResponseDTO(Order e) {
        Set<Product_Amount> products = new HashSet<>();
        for(Order_Detail od : e.getDetails()){
            products.add(new Product_Amount(od.getProduct().getId(), od.getAmount()));
        }
        OrderResponseDTO o = new OrderResponseDTO(e.getId(), e.getTotalPrice(), e.getCreatedDate(), userMapper.ToResponseDTOShort(e.getUser()), products, e.isProcessed());
        return o;
    }

    @Override
    public List<OrderResponseDTO> ToResponseDTOList(List<Order> e) {
        List<OrderResponseDTO> list = new ArrayList<>();
        for (Order p : e) {
            OrderResponseDTO d = ToResponseDTO(p);
            list.add(d);
        }
        return list;
    }

}
