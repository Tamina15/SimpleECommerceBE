/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.ProductRequestDTO;
import rookiesspring.dto.RateDTO;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.dto.response.custom.ProductPagination;
import rookiesspring.model.User;
import rookiesspring.service.ImageService;
import rookiesspring.service.ProductService;
import rookiesspring.util.Util;

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

    @GetMapping("")
    public ResponseEntity getAllProducts(@Valid ProductRequestDTO dto) {
        List<ProductResponseDTO> list_products = service.findAll(dto);
        long count = service.countAll(dto.isFeatured(), Util.toLongList(dto.getCategory_id()));
        return ResponseEntity.ok().body(new ProductPagination(list_products, count));
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findOneById(id));
    }
    @PostMapping("/")
    public ResponseEntity rateProduct(Authentication auth, @Valid @RequestBody() RateDTO rate_dto) {
        var user = (User) auth.getPrincipal();
        return ResponseEntity.ok(service.rateProduct(rate_dto, user));
    }
}
