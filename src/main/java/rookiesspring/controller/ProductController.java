/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.ProductDTO;
import rookiesspring.service.ProductService;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping({"", "/"})
    public ResponseEntity getAllProduct() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-category")
    public ResponseEntity getAllProductByCategory(@RequestBody() long[] category_id) {
        return ResponseEntity.ok(service.findAllByCategory(category_id));
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findOneById(id));
    }

    @GetMapping("/full")
    public ResponseEntity getAllProductFull() {
        return ResponseEntity.ok(service.findAllFull());
    }

    @GetMapping("/full/{id}")
    public ResponseEntity getProductByIdFull(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findOneByIdFull(id));
    }

    @PostMapping("/new")
    public ResponseEntity addProduct(@RequestBody() ProductDTO product) {
        return ResponseEntity.ok(service.save(product));
    }

    @PostMapping("/add-category")
    public ResponseEntity addCategory(@RequestParam("product_id") long product_id, @RequestParam("category_id") long category_id) {
        return ResponseEntity.ok(service.addCategory(product_id, category_id));
    }

}
