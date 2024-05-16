/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.dto.response.custom.ProductResponseDTOShort;
import rookiesspring.service.ProductService;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping({"", "/"})
    public List<ProductResponseDTOShort> getAllProduct() {
        return service.findAll();
    }

    @GetMapping("/by-category")
    public List<ProductResponseDTOShort> getAllProductByCategory(@RequestBody() long[] category_id) {
        System.out.println("/by-category");
        return service.findAllByCategory(category_id);
    }

    @GetMapping("/{id}")
    public ProductResponseDTOShort getProductById(@PathVariable("id") long id) {
        return service.findOneById(id);
    }

    @GetMapping("/full")
    public List<ProductResponseDTO> getAllProductFull() {
        return service.findAllFull();
    }

    @GetMapping("/full/{id}")
    public ProductResponseDTO getProductByIdFull(@PathVariable("id") long id) {
        return service.findOneByIdFull(id);
    }

    @PostMapping("/new")
    public ProductResponseDTO addProduct(@RequestBody() ProductDTO product) {
        return service.save(product);
    }

    @PostMapping("/add-category")
    public boolean addCategory(@RequestParam("product_id") long product_id, @RequestParam("category_id") long category_id) {
        return service.addCategory(product_id, category_id);
    }

}
