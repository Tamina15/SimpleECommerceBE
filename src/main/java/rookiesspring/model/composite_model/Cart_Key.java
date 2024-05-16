/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model.composite_model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author HP
 * @author Tamina
 */
@Embeddable
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Cart_Key {

    @Column
    private long user_id;

    @Column
    private long product_id;

    public Cart_Key(long user_id, long product_id) {
        this.user_id = user_id;
        this.product_id = product_id;
    }

}
