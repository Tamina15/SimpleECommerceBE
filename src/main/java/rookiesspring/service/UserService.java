/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rookiesspring.dto.RemoveUserDTO;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.UserRequestDTO;
import rookiesspring.dto.response.UserResponseDTO;
import rookiesspring.dto.response.custom.UserPaginationShort;
import rookiesspring.dto.response.custom.UserResponseDTOShort;
import rookiesspring.dto.update.UserUpdateDTO;
import rookiesspring.mapper.UserMapper;
import rookiesspring.mapper.UserUpdateMapper;
import rookiesspring.model.Address;
import rookiesspring.model.User;
import rookiesspring.model.UserDetail;
import rookiesspring.repository.UserRepository;
import rookiesspring.service.interfaces.UserServiceInterface;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class UserService implements UserServiceInterface, UserDetailsService {

    private UserRepository repository;
    private UserMapper mapper;
    @Autowired
    private UserUpdateMapper updateMapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserResponseDTOShort findById(Long userId) {
        return repository.findProjectedById(userId).orElseThrow(() -> new EntityNotFoundException());
    }

    @Transactional(readOnly = true)
    public UserPaginationShort findAllUser(UserRequestDTO dto) {
        PageRequest page_request = PageRequest.of(dto.getPage(), dto.getLimit(), Sort.by(Sort.Direction.fromString(dto.getOrderBy()), dto.getSortBy()));
        List<UserResponseDTOShort> users = repository.findAllProjectedBy(page_request);
        long count = repository.count();
        return new UserPaginationShort(users, count);
    }

    public UserResponseDTO getUserInfo(Long userId) {
        return mapper.ToResponseDTO(repository.findById(userId).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Deprecated
    public UserResponseDTO save(UserDTO newUser) {
        User u = mapper.toEntity(newUser);
        try {
            return mapper.ToResponseDTO(repository.save(u));
        } catch (Exception e) {
            throw new EntityExistsException();
        }
    }

    public UserResponseDTOShort updateOne(UserUpdateDTO user_dto) {
        User u = repository.findById(user_dto.id()).orElseThrow(() -> new EntityNotFoundException("No value present"));
        if (user_dto.email() != null) {
            if (checkExistEmail(user_dto.email())) {
                throw new EntityExistsException("Email has already Existed");
            }
        }
        Address address = u.getUser_detail().getAddress();
        updateMapper.updateUserAddressFromDto(user_dto, address);
        UserDetail detail = u.getUser_detail();
        updateMapper.updateUserDetailFromDto(user_dto, detail);
        updateMapper.updateUserFromDto(user_dto, u);
        detail.setAddress(address);
        u.setUser_detail(detail);
        repository.save(u);
        return mapper.ToResponseDTOShort(u);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void deleteById(RemoveUserDTO dto) {
        if (checkExist(dto.user_id())) {
            if (dto.hard()) {
                repository.deleteById(dto.user_id());
            } else {
                repository.softDeleteById(dto.user_id());
            }
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public UserResponseDTOShort blockOrUnblockUser(long user_id, boolean block) {
        User user = repository.findById(user_id).orElseThrow(() -> new EntityNotFoundException("No value present"));
        user.setBlock(block);
        repository.save(user);
        return mapper.ToResponseDTOShort(user);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void restoreUser(Long user_id) {
        if (checkExist(user_id)) {
            repository.restoreUser(user_id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }

    public boolean checkExistEmail(String email) {
        return repository.existsByEmail(email);
    }

    public boolean checkExistUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No User Found"));
    }

}
