/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import java.util.List;
import org.springframework.stereotype.Service;
import rookiesspring.model.Order;
import rookiesspring.repository.OrderRepository;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class OrderService {
    
    OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> findAll() {
return repository.findAll();    }

}
