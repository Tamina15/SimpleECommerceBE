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
import java.util.Objects;
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
public class Cart {

    @EmbeddedId
    private Cart_Key id;

    @ManyToOne
    @MapsId("userId")
    @JsonBackReference
    User user;

    @ManyToOne
    @MapsId("productId")
    @JsonBackReference
    Product product;

    private int amount = 0;

    public Cart(User user, Product product) {
        this.user = user;
        this.product = product;
        this.id = new Cart_Key(user.getId(), product.getId());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.user);
        hash = 47 * hash + Objects.hashCode(this.product);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cart other = (Cart) obj;
        if (!Objects.equals(this.user.getId(), other.user.getId())) {
            return false;
        }
        return Objects.equals(this.product.getId(), other.product.getId());
    }

}
