/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.ProductRequestDTO;
import rookiesspring.service.ProductService;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;
    

    public ProductController(ProductService service) {
        this.service = service;
    }
    
    @GetMapping("")
    public ResponseEntity getAllProducts(@Valid ProductRequestDTO dto) {
        return ResponseEntity.ok().body(service.findAll(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findOneById(id));
    }

    @GetMapping("/cart")
    public ResponseEntity getProductsByIdIn(@RequestParam("product_id") long[] id) {
        return ResponseEntity.ok(service.findAllByIdIn(id));
    }


}
