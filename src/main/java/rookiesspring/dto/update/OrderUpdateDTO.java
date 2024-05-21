/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto.update;

/**
 *
 * @author HP
 */
public record OrderUpdateDTO(long order_id, long user_id, Product_Amount[] products) {

}
