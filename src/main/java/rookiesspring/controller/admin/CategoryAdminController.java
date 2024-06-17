/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller.admin;

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
@RequestMapping("/api/v1/admin/categories")
public class CategoryAdminController {

    CategoryService service;

    public CategoryAdminController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity getAllCategory(@RequestParam(defaultValue = "", required = false) String name) {
        return ResponseEntity.ok(service.findAll(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneCategory(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("")
    public ResponseEntity addCategory(@Valid @RequestBody() CategoryDTO category) {
        System.out.println(category.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(category));
    }

    @PutMapping("")
    public ResponseEntity updateCategory(@Valid @RequestBody() CategoryUpdateDTO category) {
        return ResponseEntity.ok(service.update(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") long id) {
        service.delete(id);
        return ResponseEntity.ok().body(Util.message("Delete Succesfully"));
    }
}
