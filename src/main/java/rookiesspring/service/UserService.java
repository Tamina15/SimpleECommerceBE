/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityExistsException;
import java.util.List;
import org.springframework.stereotype.Service;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.response.UserResponseDTO;
import rookiesspring.dto.response.custom.UserResponseDTOShort;
import rookiesspring.exception.ResourceNotFoundException;
import rookiesspring.mapper.UserMapper;
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

    private UserRepository repository;
    private UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<UserResponseDTOShort> findAll() {
        return repository.findAllProjectedBy();
    }

    public UserResponseDTOShort findById(Long userId) {
        return repository.findProjectedById(userId).orElseThrow(() -> new ResourceNotFoundException());
    }

    public List<UserResponseDTOShort> findAllByUsername(String username) {
        return repository.findAllProjectedByUsernameContainsIgnoreCase(username);
    }

    public List<UserResponseDTO> findAllFull() {
        return mapper.ToResponseDTOList(repository.findAll());
    }

    public UserResponseDTO findByIdFull(Long userId) {
        return mapper.ToResponseDTO(repository.findById(userId).orElseThrow(() -> new ResourceNotFoundException()));
    }

    public UserResponseDTO save(UserDTO newUser) {
        User u = mapper.toEntity(newUser);
        try {
            return mapper.ToResponseDTO(repository.save(u));
        } catch (Exception e) {
            throw new EntityExistsException();
        }
    }

    public UserResponseDTO updateOne(User oldUser) {
        return mapper.ToResponseDTO(repository.save(oldUser));
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }
}
