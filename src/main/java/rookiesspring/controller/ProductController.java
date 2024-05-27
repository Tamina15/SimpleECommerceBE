/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.ImageDTO;
import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.ProductDTO_1;
import rookiesspring.dto.UploadImageDTO;
import rookiesspring.dto.update.ProductUpdateDTO;
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

    @GetMapping({"", "/", "/all"})
    public ResponseEntity getAllProduct(@RequestParam(required = false, value = "name") String name,
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
    public ResponseEntity updateProduct(@Valid @RequestBody() ProductUpdateDTO product) {
        return ResponseEntity.ok(service.update(product));
    }
// check

    @PostMapping("/categories")
    public ResponseEntity addCategories(@RequestParam("product_id") long product_id, @RequestParam("category_id") long[] category_id) {
        return ResponseEntity.ok().body(service.addCategories(product_id, category_id));
//        service.addCategories(product_id, category_id);
//        return ResponseEntity.ok().body("Add Category Successfully");
    }

    @PostMapping("/images/")
    public ResponseEntity addImages(@RequestBody() AddImageDTO add_image) {
//        service.addImages(add_image.product_id(), add_image.images());
//        return ResponseEntity.ok().body("Add Image Successfully");
        return ResponseEntity.ok().body(service.addImages(add_image.product_id(), add_image.images()));
    }

    @DeleteMapping("/categories")
    public ResponseEntity removeCategories(@RequestParam("product_id") long product_id, @RequestParam("category_id") long[] category_id) {
//        service.removeCategories(product_id, category_id);
//        return ResponseEntity.ok().body("Remove Category Successfully");
        return ResponseEntity.ok().body(service.removeCategories(product_id, category_id));
    }

    @DeleteMapping("/images/")
    public ResponseEntity removeImages(@RequestBody() RemoveImageDTO remove_image) {
//        service.removeImages(remove_image.product_id(), remove_image.image_id());
//        return ResponseEntity.ok().body("Remove Image Successfully");
        return ResponseEntity.ok().body(service.removeImages(remove_image.product_id(), remove_image.image_id()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") long id) {
        service.delete(id);
        return ResponseEntity.accepted().body(Util.message("Delete Successfully"));
    }

    @PostMapping("/images/upload")
    public ResponseEntity UploadImage(@Valid UploadImageDTO uploadImageDTO) {
        return imageService.uploadImage(uploadImageDTO);
    }

//    @PostMapping(value = "/images/upload2", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @PostMapping(value = "/images/upload2")
    public ResponseEntity UploadImage2(ProductDTO_1 productDTO) {
        System.out.println(productDTO.toString());
        System.out.println(Arrays.toString(productDTO.category_id));
        System.out.println(Arrays.toString(productDTO.pa));
        return ResponseEntity.ok().body("Remove Image Successfully");
//        return imageService.uploadImage(productDTO);
    }

}

record AddImageDTO(
        @Positive(message = "Product Id must not  be 0")
        long product_id,
        @NotEmpty(message = "Image must not be empty")
        ImageDTO[] images) {

}

record RemoveImageDTO(
        @Positive(message = "Product Id must not  be 0")
        long product_id,
        @NotEmpty(message = "Image Ids must not be empty")
        long[] image_id) {

}
