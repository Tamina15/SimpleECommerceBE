/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import rookiesspring.dto.OrderDTO;
import rookiesspring.dto.response.OrderResponseDTO;
import rookiesspring.model.Order;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class OrderMapper implements BaseMapper<Order, OrderDTO, OrderResponseDTO> {

    UserMapper userMapper;

    public OrderMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Order toEntity(OrderDTO dto) {
        Order o = new Order();
        return o;
    }

    @Override
    public OrderResponseDTO ToResponseDTO(Order e) {
        OrderResponseDTO o = new OrderResponseDTO(e.getId(), e.getTotalPrice(), e.getCreatedTime(), userMapper.ToResponseDTOShort(e.getUser()));
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
