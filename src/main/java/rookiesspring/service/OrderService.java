/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import rookiesspring.dto.OrderDTO;
import rookiesspring.dto.response.OrderResponseDTO;
import rookiesspring.dto.response.custom.OrderResponseDTOShort;
import rookiesspring.mapper.OrderMapper;
import rookiesspring.model.Order;
import rookiesspring.model.User;
import rookiesspring.model.composite_model.Order_Detail;
import rookiesspring.repository.OrderRepository;
import rookiesspring.repository.UserRepository;
import rookiesspring.service.interfaces.OrderServiceInterface;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class OrderService implements OrderServiceInterface {

    OrderRepository repository;
    OrderMapper mapper;
    UserRepository userRepository;

    public OrderService(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<OrderResponseDTOShort> findAll(LocalDateTime from, LocalDateTime to) {
        if (from == null) {
            from = Util.minDateTime;
        }
        if (to == null) {
            to = LocalDateTime.now();
        }
        return repository.findAllProjectedByCreatedDateBetween(from, to);
    }

    public OrderResponseDTOShort findById(long id) {
        return repository.findOneProjectedById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<OrderResponseDTO> findAllFull(LocalDateTime from, LocalDateTime to) {
        if (from == null) {
            from = Util.minDateTime;
        }
        if (to == null) {
            to = LocalDateTime.now();
        }
        return mapper.ToResponseDTOList(repository.findAllByCreatedDateBetween(from, to));
    }

    public OrderResponseDTO findByIdFull(long id) {
        return mapper.ToResponseDTO(repository.findById(id).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }

    public OrderResponseDTO save(OrderDTO order_dto) {
        Order o = mapper.toEntity(order_dto);
        if (userRepository.existsById(order_dto.user_id())) {
            User u = userRepository.getReferenceById(order_dto.user_id());
            o.setUser(u);
            o = repository.save(o);
            // Add Product through OrderDetail
            return mapper.ToResponseDTO(o);
        } else {
            throw new EntityExistsException();
        }
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void doSomethingAfterStartup() {
        List<Order> list = repository.findAll();
        for (Order o : list) {
            double price = 0;
            List<Order_Detail> od = o.getDetails();
            for (Order_Detail d : od) {
                price += d.getProduct().getPrice() * d.getAmount();
            }
            o.setTotalPrice(String.format("%f", price));
            repository.save(o);
        }
    }
}
