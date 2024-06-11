/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rookiesspring.dto.response;

import java.util.List;
import java.util.Set;
import rookiesspring.model.Address;
import rookiesspring.model.Order;

/**
 *
 * @author HP
 * @author Tamina
 */

public record UserResponseDTO(long id, String username, String email, boolean gender, int age, String phone, List<Order> orders, Address address, Set<String> roles) {

}
