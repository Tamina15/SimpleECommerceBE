/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model.composite_model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
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
public class Product_Category_Key {

    @Column()
    Long product_id;

    @Column()
    Long category_id;

    public Product_Category_Key(Long product_id, Long category_id) {
        this.product_id = product_id;
        this.category_id = category_id;
    }

}
