/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
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
import rookiesspring.service.OrderService;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 * @author Tamina
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping({"", "/"})
    public ResponseEntity getAllOrder(@RequestParam(name = "from", required = false) LocalDateTime from, @RequestParam(name = "to", required = false) LocalDateTime to) {
        return ResponseEntity.ok(service.findAll(from, to));
    }

    @GetMapping("/full")
    public ResponseEntity getAllOrderFull(@RequestParam(name = "from", required = false) LocalDateTime from, @RequestParam(name = "to", required = false) LocalDateTime to) {
        return ResponseEntity.ok(service.findAllFull(from, to));
    }

    @GetMapping("/full/{id}")
    public ResponseEntity getOrderByIdFull(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findByIdFull(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOrderByIdShort(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity createOrder(@Valid @RequestBody OrderDTO order) {
        return ResponseEntity.ok(service.save(order));
    }

    /**
     * Change to process order, todo: check product amount, subtract product
     * amount, set processed = true
     *
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity processOrder(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(service.proccessOrder(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable("id") long id) {
        service.delete(id);
        return ResponseEntity.ok().body(Util.message("Delete Succesfully"));
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
