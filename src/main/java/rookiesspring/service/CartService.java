/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookiesspring.dto.CartDTO;
import rookiesspring.dto.response.CartResponseDTO;
import rookiesspring.dto.response.OrderResponseDTO;
import rookiesspring.exception.NotEnoughProductException;
import rookiesspring.mapper.CartMapper;
import rookiesspring.mapper.OrderMapper;
import rookiesspring.model.Cart;
import rookiesspring.model.Order;
import rookiesspring.model.Product;
import rookiesspring.model.User;
import rookiesspring.model.composite_model.Order_Detail;
import rookiesspring.repository.CartRepository;
import rookiesspring.repository.OrderRepository;
import rookiesspring.repository.ProductRepository;
import rookiesspring.repository.UserRepository;
import rookiesspring.service.interfaces.BaseServiceInterface;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class CartService implements BaseServiceInterface {

    CartRepository repository;

    CartMapper mapper;

    ProductRepository productRepository;

    UserRepository userRepository;

    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;

    public CartService(CartRepository repository, CartMapper mapper, ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public List<CartResponseDTO> addToCart(CartDTO cart) {
        if (userRepository.existsById(cart.user_id()) && productRepository.existsById(cart.product_id())) {
            User u = new User();
            u.setId(cart.user_id());
            Product p = new Product(cart.product_id());
            Cart c = repository.findOneByUserIdEqualsAndProductIdEquals(cart.user_id(), cart.product_id()).orElse(new Cart(u, p));
            c.setAmount(c.getAmount() + cart.amount());
            repository.save(c);
            return mapper.ToResponseDTOList(repository.findAllByUserIdEquals(cart.user_id()));
        } else {
            throw new EntityNotFoundException();
        }
    }

    public List<CartResponseDTO> removefromCart(CartDTO cart) {
        if (checkExist(cart.user_id(), cart.product_id())) {
            User u = new User(cart.user_id());
            Product p = new Product(cart.product_id());
            Cart c = new Cart(u, p);
            repository.delete(c);
            return mapper.ToResponseDTOList(repository.findAllByUserIdEquals(cart.user_id()));
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public OrderResponseDTO buy(long user_id) {
        User u = userRepository.findById(user_id).orElseThrow(() -> new EntityNotFoundException());
        List<Cart> list = repository.findAllByUserIdEquals(user_id);
        if (list.isEmpty()) {
            return null;
        }
        Order o = new Order();
        Set<Order_Detail> set = new HashSet<>();
        o.setUser(u);
        o = orderRepository.save(o);
        double price = 0;
        for (Cart c : list) {
            Product p = c.getProduct();
            if (!p.checkAmount(c.getAmount())) {
                throw new NotEnoughProductException("Product id " + p.getId() + ", name: " + p.getName() + " doesnt have enough quantity");
            }
            Order_Detail od = new Order_Detail(o, p, c.getAmount());
            price += p.getAmount() * c.getAmount();
            p.reduceAmount(c.getAmount());
            productRepository.save(p);
            set.add(od);
        }
        o.setDetails(set);
        o.setTotalPrice(String.format("%f", price));
        o = orderRepository.save(o);
        repository.deleteByUserId(user_id);
        return orderMapper.ToResponseDTO(o);
    }

    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }

    public boolean checkExist(long user_id, long product_id) {
        return repository.existsByUserIdAndProductId(user_id, product_id);
    }
}
