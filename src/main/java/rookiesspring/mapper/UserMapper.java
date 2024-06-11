/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.response.UserResponseDTO;
import rookiesspring.dto.response.custom.UserResponseDTOShort;
import rookiesspring.dto.update.UserUpdateDTO;
import rookiesspring.model.Address;
import rookiesspring.model.User;
import rookiesspring.model.UserDetail;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class UserMapper implements BaseMapper<User, UserDTO, UserResponseDTO> {

    @Override
    public User toEntity(UserDTO dto) {
        User u = new User();
        u.setEmail(dto.email());
        u.setUsername(dto.username());
        UserDetail d = new UserDetail();
        d.setUser(u);
        d.setGender(dto.gender());
        d.setAge(dto.age());
        d.setPhone(dto.phone());
        Address a = new Address();
        a.setAddress_number(dto.address_number());
        a.setStreet(dto.street());
        a.setWard(dto.ward());
        a.setDistrict(dto.district());
        a.setCity(dto.city());
        a.setProvince(dto.province());
        a.setCountry(dto.country());
        d.setAddress(a);
        u.setUser_detail(d);
        return u;
    }

    @Override
    public UserResponseDTO ToResponseDTO(User e) {
        UserDetail d = e.getUser_detail();
        UserResponseDTO u = new UserResponseDTO(e.getId(), e.getUserName(), e.getEmail(), d.isGender(), d.getAge(), d.getPhone(), e.getOrders(), d.getAddress(), e.getRoles());
        return u;
    }

    @Override
    public List<UserResponseDTO> ToResponseDTOList(List<User> e) {
        List<UserResponseDTO> list = new ArrayList<>();
        for (User s : e) {
            UserResponseDTO u = ToResponseDTO(s);
            list.add(u);
        }
        return list;
    }

    public UserResponseDTOShort ToResponseDTOShort(User e) {
        UserResponseDTOShort u = new UserResponseDTOShort(e.getId(), e.getUserName(), e.getEmail(), e.isBlocked(), e.isDeleted(), e.getRoles());
        return u;
    }

    public List<UserResponseDTOShort> ToResponseDTOShortList(List<User> users) {
        List<UserResponseDTOShort> list = new ArrayList<>();
        for (User e : users) {
            UserResponseDTOShort u = new UserResponseDTOShort(e.getId(), e.getUserName(), e.getEmail(), e.isBlocked(), e.isDeleted(), e.getRoles());
            list.add(u);
        }
        return list;
    }

    public void toUpdateUserFromDTO(UserUpdateDTO dto, User entity) {
        if (dto == null) {
            return;
        }
        if (dto.email() != null) {
            entity.setEmail(dto.email());
        }
        if (dto.username() != null) {
            entity.setUsername(dto.username());
        }
        UserDetail detail = entity.getUser_detail();
        if (dto.firstname() != null) {
            detail.setFirstname(dto.firstname());
        }
        if (dto.lastname() != null) {
            detail.setLastname(dto.lastname());
        }
        detail.setGender(dto.gender());
        detail.setAge(dto.age());
        if (dto.phone() != null) {
            detail.setPhone(dto.phone());
        }
        Address address = detail.getAddress();
        if (dto.address_number() != null) {
            address.setAddress_number(dto.address_number());
        }
        if (dto.street() != null) {
            address.setStreet(dto.street());
        }
        if (dto.ward() != null) {
            address.setWard(dto.ward());
        }
        if (dto.district() != null) {
            address.setDistrict(dto.district());
        }
        if (dto.city() != null) {
            address.setCity(dto.city());
        }
        if (dto.province() != null) {
            address.setProvince(dto.province());
        }
        if (dto.country() != null) {
            address.setCountry(dto.country());
        }
        detail.setAddress(address);
        entity.setUser_detail(detail);
    }
}
