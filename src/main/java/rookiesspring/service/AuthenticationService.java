/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityExistsException;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rookiesspring.dto.LoginDTO;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.response.UserResponseDTO;
import rookiesspring.exception.BadRequestException;
import rookiesspring.mapper.UserMapper;
import rookiesspring.model.User;
import rookiesspring.repository.UserRepository;
import rookiesspring.util.Role;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserMapper mapper;

    public UserResponseDTO signup(UserDTO input) {
        if (!userRepository.existsByEmail(input.email())) {
            if(input.username().isBlank() || input.username().isEmpty() || input.password().isEmpty()){
                throw new BadRequestException("Username or Password can not be empty");
            }
            User user = mapper.toEntity(input);
            user.setPassword(passwordEncoder.encode(input.password()));
            Set<String> roles = Set.of(Role.USER.toString());
            user.setRoles(roles);
            return mapper.ToResponseDTO(userRepository.save(user));
        } else {
            throw new EntityExistsException("Email has been taken");
        }
    }

    public User authenticate(LoginDTO input) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.email(), input.password()));
        return userRepository.findByEmail(input.email()).orElseThrow();
    }
}
