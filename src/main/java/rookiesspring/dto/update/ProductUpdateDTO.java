/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package rookiesspring.dto.update;

import rookiesspring.dto.ImageDTO;

/**
 *
 * @author HP
 */
public record ProductUpdateDTO(long id, String name, String description, double price,int amount) {

}
