/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto.response.custom;

import java.time.LocalDateTime;

/**
 *
 * @author HP
 * @author Tamina
 */
public record OrderResponseDTOShort(long id, String totalPrice, LocalDateTime createdDate, long user_id) {

}
