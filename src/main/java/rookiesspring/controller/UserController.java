/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

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
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.CartDTO;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService service;
    private CartService cartService;

    public UserController(UserService service, CartService cartService) {
        this.service = service;
        this.cartService = cartService;
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfomation(Authentication auth) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.ok(service.findByIdFull(user_id));
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

    @PutMapping("/carts")
    public ResponseEntity addToCart(@RequestBody() CartDTO cart_dto, Authentication auth) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(user_id, cart_dto));
    }

    @DeleteMapping("/carts")
    public ResponseEntity removeFromCart(@RequestBody() CartDTO cart_dto, Authentication auth) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.accepted().body(cartService.removefromCart(user_id, cart_dto));
    }

    @PostMapping("/carts")
    public ResponseEntity buy(Authentication auth) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.status(HttpStatus.OK).body(cartService.buy(user_id));
    }

}
