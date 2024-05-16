/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rookiesspring.model.composite_model.Cart_Key;

/**
 *
 * @author HP
 * @author Tamina
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Cart {

    @EmbeddedId
    private Cart_Key id;

    @ManyToOne
    @MapsId("user_id")
    @JsonBackReference
    User user;

    @ManyToOne
    @MapsId("product_id")
    @JsonBackReference
    Product product;

    private int amount;

    public Cart(User user, Product product) {
        this.user = user;
        this.product = product;
        this.id = new Cart_Key(user.getId(), product.getId());
    }

}
