/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.service.CategoryService;

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

    @GetMapping({"", "/"})
    public ResponseEntity getAllCategory(@RequestParam(defaultValue = "", required = false) String name) {
        return ResponseEntity.ok(service.findAll(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneCategory(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
