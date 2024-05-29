/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.ProductRequestDTO;
import rookiesspring.service.ImageService;
import rookiesspring.service.ProductService;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService service;
    @Autowired
    private ImageService imageService;

    public ProductController(ProductService service) {
        this.service = service;
    }

//    @GetMapping("")
//    public ResponseEntity getAllProducts(@RequestParam(required = false, value = "name") String name,
//            @RequestParam(value = "category_id", required = false) long[] category_id,
//            @RequestParam(name = "from", required = false) LocalDateTime from,
//            @RequestParam(name = "to", required = false) LocalDateTime to) {
//        return ResponseEntity.ok(service.findAll(name, category_id, from, to));
//    }
    @GetMapping("")
    public ResponseEntity getAllProducts(@Valid ProductRequestDTO dto) {
        System.out.println(dto.toString());
        return ResponseEntity.ok(service.findAll(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findOneById(id));
    }
}
