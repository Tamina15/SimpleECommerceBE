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
        UserResponseDTO u = new UserResponseDTO(e.getId(), e.getUsername(), e.getEmail(), d.isGender(), d.getAge(), d.getPhone(), e.getOrders(), d.getAddress());
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
        UserResponseDTOShort u = new UserResponseDTOShort(e.getId(), e.getUsername(), e.getEmail());
        return u;
    }
}
