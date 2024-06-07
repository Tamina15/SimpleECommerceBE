/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rookiesspring.dto.LoginDTO;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.response.LoginResponseDTO;
import rookiesspring.mapper.UserMapper;
import rookiesspring.model.User;
import rookiesspring.repository.UserRepository;
import rookiesspring.model.Role;

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

    private final JwtService jwtService;

    @Transactional(rollbackFor = {RuntimeException.class})
    public LoginResponseDTO signup(UserDTO input) {
        if (!userRepository.existsByEmail(input.email())) {
            User user = mapper.toEntity(input);
            user.setPassword(passwordEncoder.encode(input.password()));
            Set<String> roles = Set.of(Role.USER.toString());
            user.setRoles(roles);
            user = userRepository.save(user);
            String jwtToken = jwtService.generateToken(user);
            LoginResponseDTO loginResponse = new LoginResponseDTO(jwtToken, jwtService.getExpirationTime());
            return loginResponse;
//            return mapper.ToResponseDTO(userRepository.save(user));
        } else {
            throw new EntityExistsException("Email has been taken");
        }
    }

    public LoginResponseDTO authenticate(LoginDTO input) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.email(), input.password()));

        User authenticatedUser = userRepository.findByEmail(input.email()).orElseThrow();
        if(authenticatedUser.isBlock() || authenticatedUser.isDeleted()){
            System.out.println("User Deleted or Blocked");
            throw new EntityNotFoundException();
        }
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO(jwtToken, jwtService.getExpirationTime());
        return loginResponse;
    }
}
