/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.response.UserResponseDTO;
import rookiesspring.model.User;
import rookiesspring.repository.UserRepository;
import rookiesspring.service.interfaces.UserServiceInterface;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository repository;

    public List<UserResponseDTO> findAll() {
        List<User> list = repository.findAll();
        List<UserResponseDTO> d = new ArrayList<>();
        for (User u : list) {
            d.add(ToResponseDTO(u));
        }
        return d;
    }

    public UserResponseDTO findById(Long userId) {
        return ToResponseDTO(repository.findById(userId).orElseThrow());
    }

    public UserResponseDTO save(UserDTO newUser) {
        User u = toEntity(newUser);
        u= repository.save(u);
        return ToResponseDTO(u);
    }

    public boolean checkExist(Long id) {
        return repository.findById(id).isPresent();
    }

    public UserResponseDTO updateOne(User oldUser) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public UserResponseDTO ToResponseDTO(User e) {
        UserResponseDTO u = new UserResponseDTO(e.getId(), e.getUsername(), e.getEmail(), e.isGender(), e.getAge(), e.getPhone(), e.getAddress());
        return u;
    }

    public User toEntity(UserDTO dto) {
        User u = new User();
        u.setUsername(dto.username());
        u.setEmail(dto.email());
        u.setGender(dto.gender());
        u.setAge(dto.age());
        u.setPhone(dto.phone());
        return u;
    }
}
