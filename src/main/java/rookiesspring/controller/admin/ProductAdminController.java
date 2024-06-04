/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller.admin;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.ProductRequestDTO;
import rookiesspring.dto.RemoveImageDTO;
import rookiesspring.dto.UploadImageDTO;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.dto.response.custom.ProductPagination;
import rookiesspring.dto.update.ProductUpdateDTO;
import rookiesspring.service.ImageService;
import rookiesspring.service.ProductService;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductAdminController {

    private final ProductService service;
    @Autowired
    private ImageService imageService;

    public ProductAdminController(ProductService service) {
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

    @PostMapping("")
    public ResponseEntity addProduct(@RequestBody() ProductDTO product) {
        return ResponseEntity.ok(service.save(product));
    }

    @PutMapping("")
    public ResponseEntity updateProduct(@Valid @RequestBody() ProductUpdateDTO product) {
        return ResponseEntity.ok(service.update(product));
    }

    @PostMapping("/categories")
    public ResponseEntity addCategories(@RequestParam("product_id") long product_id, @RequestParam("category_id") long[] category_id) {
        return ResponseEntity.ok().body(service.addCategories(product_id, category_id));
    }

    @DeleteMapping("/categories")
    public ResponseEntity removeCategories(@RequestParam("product_id") long product_id, @RequestParam("category_id") long[] category_id) {
        return ResponseEntity.ok().body(service.removeCategories(product_id, category_id));
    }

    @PostMapping("/images")
    public ResponseEntity uploadImage(@Valid UploadImageDTO uploadImageDTO) {
        System.out.println(uploadImageDTO.toString());
        return ResponseEntity.ok().body(imageService.uploadImage(uploadImageDTO));
    }

    @DeleteMapping("/images")
    public ResponseEntity removeImages(@Valid @RequestBody() RemoveImageDTO remove_image) {
        return ResponseEntity.ok().body(imageService.removeImages(remove_image.product_id(), remove_image.image_id(), remove_image.hard()));
    }

    @PatchMapping("/images/{id}")
    public ResponseEntity restoreImages(@PathVariable("id") long image_id) {
        imageService.restoreImages(image_id);
        return ResponseEntity.ok().body(Util.message("Restores Image Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") long id, @RequestParam("forced") boolean forcedDelete) {
        service.delete(id, forcedDelete);
        return ResponseEntity.accepted().body(Util.message("Delete Successfully"));
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity restoreProduct(@PathVariable("id") long id) {
        service.restore(id);
        return ResponseEntity.accepted().body(Util.message("Delete Successfully"));
    }

}
