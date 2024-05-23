/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
import rookiesspring.dto.CategoryDTO;
import rookiesspring.dto.update.CategoryUpdateDTO;
import rookiesspring.service.CategoryService;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 * @author Tamina
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    /**
     * Find All Category, Not Include Product
     *
     * @param name
     * @return List of all Categories
     */
    @GetMapping({"", "/", "/all"})
    public ResponseEntity getAllCategory(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(service.findAll(name));
    }

    /**
     * Find One Category With {@code Id}
     *
     * @param id
     * @return Category With Id
     * @throws ResourceNotFoundException()
     */
    @GetMapping("/{id}")
    public ResponseEntity getOneCategory(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Find All Category, Include Products in it Short Form to make No Extra
     * Call to Database
     *
     * @param name
     * @return List of all Categories with associated Products
     */
    @GetMapping("/full")
    public ResponseEntity getAllCategoryFull(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(service.findAllFull(name));
    }

    @GetMapping("/full/{id}")
    public ResponseEntity getOneCategoryFull(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(service.findByIdFull(id));
    }

    @PostMapping("/")
    public ResponseEntity addCategory(@RequestBody() CategoryDTO category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(category));
    }

    @PutMapping("/")
    public ResponseEntity updateCategory(@Valid @RequestBody() CategoryUpdateDTO category) {
        return ResponseEntity.ok(service.update(category));
    }

    @PostMapping("/products")
    public ResponseEntity addProduct(@RequestBody() CD_ID id) {
        return ResponseEntity.ok(service.addProduct(id.category_id(), id.product_ids()));
    }

    @DeleteMapping("/products")
    public ResponseEntity removeProduct(@RequestBody() CD_ID id) {
        return ResponseEntity.ok(service.removeProduct(id.category_id(), id.product_ids()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") long id) {
        if (service.delete(id)) {
            return ResponseEntity.ok().body(Util.message("Delete Succesfully"));
        }
        throw new EntityNotFoundException();
    }
}

record CD_ID(long category_id, long[] product_ids) {

}
