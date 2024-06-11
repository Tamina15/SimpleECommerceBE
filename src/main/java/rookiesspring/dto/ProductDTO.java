/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto;

/**
 *
 * @author HP
 */
public record ProductDTO(String name, String description, double price, int amount, long[] category_id, boolean feature) {

}
