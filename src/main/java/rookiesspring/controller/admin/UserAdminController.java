/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.RemoveUserDTO;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.UserRequestDTO;
import rookiesspring.dto.response.custom.UserResponseDTOShort;
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
    
    @GetMapping("")
    public ResponseEntity getAllUsers(UserRequestDTO dto) {
        return ResponseEntity.ok(service.findAllUser(dto));
    }
    
    @GetMapping("/info")
    public ResponseEntity getUserInfomation(Authentication auth) {
        var user = (User) auth.getPrincipal();
        long user_id = user.getId();
        return ResponseEntity.ok(service.getUserInfo(user_id));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable(value = "id") Long user_id) {
        return ResponseEntity.ok(service.findById(user_id));
    }
    
    @PostMapping("")
    public ResponseEntity createUser(@Valid @RequestBody UserDTO newUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAdmin(newUser));
    }
    
    @PutMapping("/")
    public ResponseEntity changeUserInfomation(@Valid @RequestBody UserUpdateDTO user_dto) {
        return ResponseEntity.ok(service.updateOne(user_dto));
    }
    
    @PatchMapping("/{id}/{block}")
    public ResponseEntity blockOrUnblockUser(@PathVariable(value = "id") Long user_id, @PathVariable(value = "block") boolean block) {
        UserResponseDTOShort response = service.blockOrUnblockUser(user_id, block);
        return ResponseEntity.ok(response);
//        return ResponseEntity.ok(Util.message(block ? "Block" : "UnBlock" + "User Successfullly"));
    }
    
    @DeleteMapping("")
    public ResponseEntity DeleteUser(@Valid @RequestBody() RemoveUserDTO remove_user_dto) {
        service.deleteById(remove_user_dto);
        return ResponseEntity.accepted().body(Util.message("Delete Successfully"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity RestoreUser(@PathVariable(value = "id") Long user_id) {
        service.restoreUser(user_id);
        return ResponseEntity.accepted().body(Util.message("Restore Successfully"));
    }
    
}
