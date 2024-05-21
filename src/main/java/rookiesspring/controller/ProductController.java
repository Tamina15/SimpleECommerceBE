/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
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
import rookiesspring.dto.ImageDTO;
import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.update.ProductUpdateDTO;
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

    @GetMapping({"", "/", "/all"})
    public ResponseEntity getAllProduct( @RequestParam(required = false, value = "name") String name,
            @RequestParam(value = "category_id", required = false) long[] category_id,
            @RequestParam(name = "from", required = false) LocalDateTime from,
            @RequestParam(name = "to", required = false) LocalDateTime to) {
        System.out.println(name + " " + Arrays.toString(category_id) + " " + from + " " + to);
        return ResponseEntity.ok(service.findAll(name, category_id, from, to));
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findOneById(id));
    }

    @GetMapping("/full/{id}")
    public ResponseEntity getProductByIdFull(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findOneByIdFull(id));
    }

    @PostMapping("/")
    public ResponseEntity addProduct(@RequestBody() ProductDTO product) {
        return ResponseEntity.ok(service.save(product));
    }

    /**
     * Need Condition check
     *
     * @param product
     * @return
     */
    @PutMapping("/")
    public ResponseEntity updateProduct(@RequestBody() ProductUpdateDTO product) {
        return ResponseEntity.ok(service.update(product));
    }
// check

    @PostMapping("/category")
    public ResponseEntity addCategories(@RequestParam("product_id") long product_id, @RequestParam("category_id") long[] category_id) {
        service.addCategories(product_id, category_id);
        return ResponseEntity.ok().body("Add Category Successfully");
    }

    @PostMapping("/image/")
    public ResponseEntity addImages(@RequestBody() AddImageDTO add_image) {
        service.addImages(add_image.product_id(), add_image.images());
        return ResponseEntity.ok().body("Add Image Successfully");
    }

    @DeleteMapping("/category")
    public ResponseEntity removeCategories(@RequestParam("product_id") long product_id, @RequestParam("category_id") long[] category_id) {
        service.removeCategories(product_id, category_id);
        return ResponseEntity.ok().body("Remove Category Successfully");
    }

    @DeleteMapping("/image/")
    public ResponseEntity removeImages(@RequestBody() RemoveImageDTO remove_image) {
        System.out.println(Arrays.toString(remove_image.image_id()));
        service.removeImages(remove_image.product_id(), remove_image.image_id());
        return ResponseEntity.ok().body("Remove Image Successfully");
    }

    @DeleteMapping("/")
    public ResponseEntity deleteProduct(@RequestBody() long id) {
        service.delete(id);
        return ResponseEntity.accepted().body("Delete Successfully");
    }

    @PostMapping("/add-image")
    public void AddImage() {

    }

}

record AddImageDTO(long product_id, ImageDTO[] images) {

}

record RemoveImageDTO(long product_id, long[] image_id) {

}
