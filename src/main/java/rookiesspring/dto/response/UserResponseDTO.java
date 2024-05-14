/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rookiesspring.dto.response;

import rookiesspring.model.Address;

/**
 *
 * @author HP
 * @author Tamina
 */

public record UserResponseDTO(long id, String username, String email, boolean gender, int age, String phone, Address address) {

}
