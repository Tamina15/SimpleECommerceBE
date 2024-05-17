/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.OrderDTO;
import rookiesspring.service.OrderService;

/**
 *
 * @author HP
 * @author Tamina
 */
@RestController
@RequestMapping("/order")
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
    public ResponseEntity createOrder(@RequestBody OrderDTO order) {
        return ResponseEntity.ok(service.save(order));
    }
}
