/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto.response.custom;

import java.util.Set;

/**
 *
 * @author HP
 */
public record UserResponseDTOShort(long id, String username, String email, boolean blocked, boolean deleted, Set<String> roles) {

}
