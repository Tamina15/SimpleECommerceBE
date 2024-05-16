/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
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
import rookiesspring.dto.response.UserResponseDTO;
import rookiesspring.dto.response.custom.UserResponseDTOShort;
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
    public List<UserResponseDTOShort> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/full")
    public List<UserResponseDTO> getAllUsersFull() {
        return service.findAllFull();
    }

    @GetMapping("/search")
    public List<UserResponseDTOShort> getAllUserByName(@RequestParam("username") String username) {
        return service.findAllByUsername(username);
    }

    @GetMapping("/full/{id}")
    public UserResponseDTO getUserFull(@PathVariable(value = "id") Long userId) {
        return service.findByIdFull(userId);
    }

    @GetMapping("/{id}")
    public UserResponseDTOShort getUser(@PathVariable(value = "id") Long userId) {
        return service.findById(userId);
    }

    @PostMapping("/new")
    public UserResponseDTO createUser(@RequestBody UserDTO newUser) {
        return service.save(newUser);
    }

    @PutMapping("/change-infomation")
    public UserResponseDTO changeUserInfomation(@RequestBody User oldUser) {
        if (service.checkExist(oldUser.getId())) {
            return service.updateOne(oldUser);
        } else {
            throw new NoSuchElementException("No value present");
        }
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void DeleteUser(@RequestParam("id") long id) {
        if (service.checkExist(id)) {
            service.deleteById(id);
        } else {
            throw new NoSuchElementException("No value present");
        }
    }
}
