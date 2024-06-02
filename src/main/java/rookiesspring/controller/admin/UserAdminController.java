/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.update.UserUpdateDTO;
import rookiesspring.model.User;
import rookiesspring.service.CartService;
import rookiesspring.service.UserService;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 * @author Tamina
 */
@RestController
@RequestMapping("/api/v1/admin/users")
public class UserAdminController {

    private final UserService service;

    public UserAdminController(UserService service, CartService cartService) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity getAllUsers(@RequestParam(value = "username", required = false) String username) {
        return ResponseEntity.ok(service.findAllByUsername(username));
    }

    @GetMapping("/orders/")
    public ResponseEntity getAllUsersWithOrders(@RequestParam(value = "username", required = false) String username) {
        return ResponseEntity.ok(service.findAllFull(username));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity getUserWithOrders(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(service.findByIdFull(userId));
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfomation(Authentication auth) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.ok(service.findByIdFull(user_id));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(service.findById(userId));
    }

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody UserDTO newUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(newUser));
    }

    @PutMapping("/")
    public ResponseEntity changeUserInfomation(@RequestBody UserUpdateDTO user_dto) {
        return ResponseEntity.ok(service.updateOne(user_dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity DeleteUser(@PathVariable(value = "id") Long id) {
        service.deleteById(id);
        return ResponseEntity.accepted().body(Util.message("Delete Successfully"));
    }

}
