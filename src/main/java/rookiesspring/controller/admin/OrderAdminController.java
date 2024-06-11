/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller.admin;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.OrderDTO;
import rookiesspring.dto.update.OrderUpdateDTO;
import rookiesspring.model.User;
import rookiesspring.service.OrderService;

/**
 *
 * @author HP
 * @author Tamina
 */
@RestController
@RequestMapping("/api/v1/admin/orders")
public class OrderAdminController {

    OrderService service;

    public OrderAdminController(OrderService service) {
        this.service = service;
    }

    @GetMapping({"", "/"})
    public ResponseEntity getAllOrder(Authentication auth, @RequestParam(name = "from", required = false) LocalDateTime from, @RequestParam(name = "to", required = false) LocalDateTime to) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.ok(service.findAll(user_id, from, to));
    }

    @GetMapping("/products")
    public ResponseEntity getAllOrderWithProducts(Authentication auth, @RequestParam(name = "from", required = false) LocalDateTime from, @RequestParam(name = "to", required = false) LocalDateTime to) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.ok(service.findAllFull(user_id, from, to));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity getOrderByIdWithProducts(Authentication auth, @PathVariable("id") long order_id) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.ok(service.findByIdFull(user_id, order_id));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOrderById(Authentication auth, @PathVariable("id") long order_id) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.ok(service.findById(user_id, order_id));
    }

    @PostMapping("/")
    public ResponseEntity createOrder(Authentication auth, @Valid @RequestBody OrderDTO order) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.ok(service.save(order, user_id));
    }

    /**
     * Change to process order, todo: check product amount, subtract product
     * amount, set processed = true
     *
     * @param order_id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity processOrder(@PathVariable("id") long order_id) {
        return ResponseEntity.ok().body(service.proccessOrder(order_id));
    }

    @PostMapping("/products")
    public ResponseEntity addProduct(@RequestBody OrderUpdateDTO dto) {
        return ResponseEntity.ok(service.addProduct(dto));
    }

    @DeleteMapping("/products")
    public ResponseEntity deleteProduct(@RequestBody OrderUpdateDTO dto) {
        return ResponseEntity.ok(service.deleteProduct(dto));
    }
}
