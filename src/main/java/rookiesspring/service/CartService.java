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
import rookiesspring.exception.EmptyCartException;
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

    public List<CartResponseDTO> addToCart(long user_id, CartDTO cart_DTO) {
        Cart cart;
        if (checkExist(user_id, cart_DTO.product_id())) {
            cart = repository.findOneByUserIdEqualsAndProductIdEquals(user_id, cart_DTO.product_id()).get();
            cart.setAmount(cart.getAmount() + cart_DTO.amount());
        } else {
            User user = new User();
            user.setId(user_id);
            Product product = new Product(cart_DTO.product_id());
            cart = new Cart(user, product);
            cart.setAmount(cart_DTO.amount());
        }
        repository.save(cart);
        return mapper.ToResponseDTOList(repository.findAllByUserIdEquals(user_id));

    }

    public List<CartResponseDTO> removefromCart(long user_id, CartDTO cart_DTO) {
        Cart cart = repository.findOneByUserIdEqualsAndProductIdEquals(user_id, cart_DTO.product_id()).orElse(new Cart());
        if (cart.getAmount() > cart_DTO.amount()) {
            cart.setAmount(cart_DTO.amount() - cart.getAmount());
            repository.save(cart);
        } else {
            User user = new User(user_id);
            Product product = new Product(cart_DTO.product_id());
            cart = new Cart(user, product);
            repository.delete(cart);
        }
        return mapper.ToResponseDTOList(repository.findAllByUserIdEquals(user_id));
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public OrderResponseDTO buy(long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new EntityNotFoundException());
        List<Cart> list = repository.findAllByUserIdEquals(user_id);
        if (list.isEmpty()) {
            throw new EmptyCartException("Your Cart Is Empty");
        }
        Order order = new Order();
        Set<Order_Detail> set = new HashSet<>();
        order.setUser(user);
        order = orderRepository.save(order);
        double price = 0;
        for (Cart cart : list) {
            Product product = cart.getProduct();
            if (!product.checkAmount(cart.getAmount())) {
                throw new NotEnoughProductException("Product id " + product.getId() + ", name: " + product.getName() + " doesnt have enough quantity");
            }
            Order_Detail order_detail = new Order_Detail(order, product, cart.getAmount());
            price += product.getPrice() * cart.getAmount();
            product.reduceAmount(cart.getAmount());
            productRepository.save(product);
            set.add(order_detail);
        }
        order.setDetails(set);
        order.setTotalPrice(String.format("%f", price));
        order = orderRepository.save(order);
        repository.deleteByUserId(user_id);
        return orderMapper.ToResponseDTO(order);
    }

    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }

    public boolean checkExist(long user_id, long product_id) {
        return repository.existsByUserIdAndProductId(user_id, product_id);
    }
}
