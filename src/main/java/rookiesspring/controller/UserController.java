/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

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
import rookiesspring.dto.CartDTO;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.update.UserUpdateDTO;
import rookiesspring.service.UserService;
import rookiesspring.util.ResponseObject;
import rookiesspring.util.Util;

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
    public ResponseEntity getAllUsers(@RequestParam(value = "username", required = false) String username) {
        return ResponseEntity.ok(service.findAllByUsername(username));
    }

    @GetMapping("/full")
    public ResponseEntity getAllUsersFull(@RequestParam(value = "username", required = false) String username) {
        return ResponseEntity.ok(service.findAllFull(username));
    }

    @GetMapping("/full/{id}")
    public ResponseEntity getUserFull(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(service.findByIdFull(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(service.findById(userId));
    }

    @PostMapping({"", "/"})
    public ResponseEntity createUser(@RequestBody UserDTO newUser) {
        return ResponseEntity.ok(service.save(newUser));
    }

    @PutMapping({"", "/"})
    public ResponseEntity changeUserInfomation(@RequestBody UserUpdateDTO user_dto) {
        return ResponseEntity.ok(service.updateOne(user_dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity DeleteUser(@PathVariable(value = "id") Long id) {
        service.deleteById(id);
        return ResponseEntity.accepted().body(Util.message("Delete Successfully"));
    }
    
    }
    
    
    
    
}
