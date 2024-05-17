/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import jakarta.persistence.EntityNotFoundException;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.UserDTO;
import rookiesspring.model.User;
import rookiesspring.service.UserService;

/**
 *
 * @author HP
 * @author Tamina
 */
@RestController
@RequestMapping("/user")

public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping({"", "/", "/all"})
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/full")
    public ResponseEntity getAllUsersFull() {
        return ResponseEntity.ok(service.findAllFull());
    }

    @GetMapping("/search")
    public ResponseEntity getAllUserByName(@RequestParam("username") String username) {
        return ResponseEntity.ok(service.findAllByUsername(username));
    }

    @GetMapping("/full/{id}")
    public ResponseEntity getUserFull(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(service.findByIdFull(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(service.findById(userId));
    }

    @PostMapping("/new")
    public ResponseEntity createUser(@RequestBody UserDTO newUser) {
        return ResponseEntity.ok(service.save(newUser));
    }

    @PutMapping("/change-infomation")
    public ResponseEntity changeUserInfomation(@RequestBody User oldUser) {
        if (service.checkExist(oldUser.getId())) {
            return ResponseEntity.ok(service.updateOne(oldUser));
        } else {
            throw new EntityNotFoundException("No value present");
        }
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity DeleteUser(@RequestParam("id") long id) {
        if (service.checkExist(id)) {
            service.deleteById(id);
            return ResponseEntity.accepted().body("Delete Successfully");
        } else {
            throw new EntityNotFoundException("No value present");
        }
    }
}
