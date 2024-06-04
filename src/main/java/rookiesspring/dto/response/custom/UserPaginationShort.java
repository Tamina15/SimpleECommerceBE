/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto.response.custom;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author HP
 * @author Tamina
 */
@Data
@AllArgsConstructor
public class UserPaginationShort {

    List<UserResponseDTOShort> users;
    long count;

}
