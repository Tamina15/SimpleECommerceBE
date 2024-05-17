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
import rookiesspring.dto.CategoryDTO;
import rookiesspring.service.CategoryService;

/**
 *
 * @author HP
 * @author Tamina
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    /**
     * Find All Category, Not Include Product
     *
     * @return List of all Categories
     */
    @GetMapping({"", "/", "/all"})
    public ResponseEntity getAllCategory() {
        return ResponseEntity.ok(service.findAll());
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
     * @return List of all Categories with associated Products
     */
    @GetMapping("/full")
    public ResponseEntity getAllCategoryFull() {
        return ResponseEntity.ok(service.findAllFull());
    }

    @GetMapping("/full/{id}")
    public ResponseEntity getOneCategoryFull(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(service.findByIdFull(id));
    }

    @GetMapping("/search")
    public ResponseEntity findAllCategoryByName(@RequestParam() String name) {
        return ResponseEntity.ok(service.findAllByName(name));
    }

    @PostMapping("/new")
    public ResponseEntity addCategory(@RequestBody() CategoryDTO category) {
        return ResponseEntity.ok(service.save(category));
    }

    @PostMapping("/add-product")
    public ResponseEntity addProduct(@RequestBody() CD_ID id) {
        return ResponseEntity.ok(service.addProduct(id.category_id(), id.product_ids()));
    }

}

record CD_ID(long category_id, long[] product_ids) {

}
