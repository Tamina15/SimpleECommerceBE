/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto.update;

/**
 *
 * @author HP
 * @author Tamina
 */
public record UserUpdateDTO(long id, String username, String email, String firstname, String lastname, boolean gender, int age, String phone, String address_number, String street, String ward, String district, String city, String province, String country) {

}
