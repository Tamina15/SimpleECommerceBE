/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rookiesspring.dto;

import rookiesspring.dto.update.Product_Amount;

/**
 *
 * @author HP
 */
public record OrderDTO (long user_id, Product_Amount[] products) {
    
}
