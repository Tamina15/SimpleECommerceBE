/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.CategoryDTO;
import rookiesspring.dto.response.CategoryResponseDTO;
import rookiesspring.dto.response.custom.CategoryResponseDTOShort;
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
    public List<CategoryResponseDTOShort> getAllCategory() {
        return service.findAll();
    }

    /**
     * Find One Category With {@code Id}
     * @param id
     * @return Category With Id
     * @throws ResourceNotFoundException()
     */
    @GetMapping("/{id}")
    public CategoryResponseDTOShort getOneCategory(@PathVariable(name = "id") long id) {
        return service.findById(id);
    }
    /**
     * Find All Category, Include Product in Short Form to make
     * No Extra Call to Database
     * @return List of all Categories with associated Products
     */
    @GetMapping("/full")
    public List<CategoryResponseDTO> getAllCategoryFull() {
        return service.findAllFull();
    }

    @GetMapping("/full/{id}")
    public CategoryResponseDTO getOneCategoryFull(@PathVariable(name = "id") long id) {
        return service.findByIdFull(id);
    }

    @GetMapping("/search")
    public List<CategoryResponseDTOShort> findAllCategoryByName(@RequestParam() String name) {
        return service.findAllByName(name);
    }

    @PostMapping("/new")
    public CategoryResponseDTO addCategory(@RequestBody() CategoryDTO category) {
        return service.save(category);
    }

    @PostMapping("/add-product")
    public CategoryResponseDTO addProduct(@RequestBody() CD_ID id) {
        return service.addProduct(id.category_id(), id.product_ids());
    }

}

record CD_ID(long category_id, long[] product_ids) {

}
