/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rookiesspring.dto.LoginDTO;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.response.LoginResponseDTO;
import rookiesspring.dto.response.UserResponseDTO;
import rookiesspring.service.AuthenticationService;

/**
 *
 * @author HP
 * @author Tamina
 */
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity register(@Valid @RequestBody UserDTO registerUserDto) {
        LoginResponseDTO registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok().body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity authenticate(@Valid @RequestBody LoginDTO loginUserDto) {
        return ResponseEntity.ok(authenticationService.authenticate(loginUserDto));
    }
}
