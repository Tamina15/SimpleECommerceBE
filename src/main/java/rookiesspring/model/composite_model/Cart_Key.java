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
public class Cart_Key {

    @Column
    private long userId;

    @Column
    private long productId;

    public Cart_Key(long user_id, long product_id) {
        this.userId = user_id;
        this.productId = product_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (int) (this.userId ^ (this.userId >>> 32));
        hash = 23 * hash + (int) (this.productId ^ (this.productId >>> 32));
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
        final Cart_Key other = (Cart_Key) obj;
        if (this.userId != other.userId) {
            return false;
        }
        return this.productId == other.productId;
    }

}
