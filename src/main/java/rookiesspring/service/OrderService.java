/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import rookiesspring.dto.OrderDTO;
import rookiesspring.dto.response.OrderResponseDTO;
import rookiesspring.dto.response.custom.OrderResponseDTOShort;
import rookiesspring.dto.update.OrderUpdateDTO;
import rookiesspring.dto.update.Product_Amount;
import rookiesspring.exception.NotEnoughProductException;
import rookiesspring.mapper.OrderMapper;
import rookiesspring.model.Order;
import rookiesspring.model.Product;
import rookiesspring.model.User;
import rookiesspring.model.composite_model.Order_Detail;
import rookiesspring.repository.OrderDetailRepository;
import rookiesspring.repository.OrderRepository;
import rookiesspring.repository.ProductRepository;
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

    private OrderRepository repository;
    private OrderDetailRepository orderDetailRepository;
    private OrderMapper mapper;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public OrderService(OrderRepository repository, OrderDetailRepository orderDetailRepository, OrderMapper mapper, UserRepository userRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.orderDetailRepository = orderDetailRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public List<OrderResponseDTOShort> findAll(long user_id, LocalDateTime from, LocalDateTime to) {
        if (from == null) {
            from = Util.minDateTime;
        }
        if (to == null) {
            to = LocalDateTime.now();
        }
        return repository.findAllProjectedByCreatedDateBetweenAndUserIdEquals(from, to, user_id);
    }

    public OrderResponseDTOShort findById(long user_id, long order_id) {
        return repository.findOneProjectedByIdAndUserIdEquals(order_id, user_id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<OrderResponseDTO> findAllFull(long user_id, LocalDateTime from, LocalDateTime to) {
        if (from == null) {
            from = Util.minDateTime;
        }
        if (to == null) {
            to = LocalDateTime.now();
        }
        return mapper.ToResponseDTOList(repository.findAllByCreatedDateBetweenAndUserIdEquals(from, to, user_id));
    }

    public OrderResponseDTO findByIdFull(long user_id, long order_id) {
        return mapper.ToResponseDTO(repository.findByIdAndUserIdEquals(user_id, order_id).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Transactional
    public OrderResponseDTO save(OrderDTO order_dto, long user_id) {
        Order o = mapper.toEntity(order_dto);
        User u = userRepository.getReferenceById(user_id);
        o.setUser(u);
        o = repository.save(o);
        Product_Amount[] list = order_dto.products();
        if (list != null) {
            for (Product_Amount pa : list) {
                repository.addProduct(o.getId(), pa.product_id(), pa.amount());
            }
        }
        Set<Order_Detail> set = new HashSet<>(orderDetailRepository.findAllByOrderId(o.getId()));
        o.setDetails(set);
        return mapper.ToResponseDTO(o);
    }

    @Deprecated
    public OrderResponseDTO update(OrderUpdateDTO dto) {
        if (repository.existsById(dto.order_id())) {
            Order o = repository.getReferenceById(dto.order_id());
            return mapper.ToResponseDTO(o);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void delete(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public OrderResponseDTO addProduct(OrderUpdateDTO dto) {
        if (repository.existsById(dto.order_id())) {
            Order o = repository.getReferenceById(dto.order_id());
            Product_Amount[] list = dto.products();
            if (list != null) {
                for (Product_Amount pa : list) {
                    Order_Detail od = new Order_Detail(o, new Product(pa.product_id()));
                    if (o.getDetails().contains(od)) {
                        repository.updateProduct(o.getId(), pa.product_id(), pa.amount());
                    } else {
                        repository.addProduct(o.getId(), pa.product_id(), pa.amount());
                    }
                }
            }
            return mapper.ToResponseDTO(repository.findById(dto.order_id()).get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public OrderResponseDTO deleteProduct(OrderUpdateDTO dto) {
        if (repository.existsById(dto.order_id())) {
            Order o = repository.getReferenceById(dto.order_id());

            return mapper.ToResponseDTO(o);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public OrderResponseDTO proccessOrder(long id) {
        if (repository.existsById(id)) {
            Order o = repository.getReferenceById(id);
            if(o.isProcessed()){
                return mapper.ToResponseDTO(o);
            }
            double price = 0;
            Set<Order_Detail> od = o.getDetails();
            for (Order_Detail d : od) {
                Product p = d.getProduct();
                if (p.checkAmount(d.getAmount())) {
                    price += p.getPrice() * d.getAmount();
                } else {
                    throw new NotEnoughProductException("Product id " + p.getId() + ", name: " + p.getName() + " doesnt have enough quantity");
                }
                p.setAmount(d.getAmount() - d.getAmount());
                productRepository.save(p);
            }
            o.setTotalPrice(String.format("%f", price));
            o.setProcessed(true);
            repository.save(o);
            return mapper.ToResponseDTO(o);
        } else {
            throw new EntityNotFoundException();
        }
    }


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void doSomethingAfterStartup() {
//        List<Order> list = repository.findAll();
//        for (Order o : list) {
//            double price = 0;
//            Set<Order_Detail> od = o.getDetails();
//            for (Order_Detail d : od) {
//                price += d.getProduct().getPrice() * d.getAmount();
//            }
//            o.setTotalPrice(String.format("%f", price));
//            repository.save(o);
//        }
//    }

//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void doSomethingAfterStartup() {
//        List<Order> list = repository.findAll();
//        for (Order o : list) {
//            double price = 0;
//            Set<Order_Detail> od = o.getDetails();
//            for (Order_Detail d : od) {
//                price += d.getProduct().getPrice() * d.getAmount();
//            }
//            o.setTotalPrice(String.format("%f", price));
//            repository.save(o);
//        }
//    }
}
