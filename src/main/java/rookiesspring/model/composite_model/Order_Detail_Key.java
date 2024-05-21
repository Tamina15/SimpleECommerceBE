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
public class Order_Detail_Key {

    @Column
    private long order_id;

    @Column
    private long product_id;

    public Order_Detail_Key(long order_id, long product_id) {
        this.order_id = order_id;
        this.product_id = product_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (int) (this.order_id ^ (this.order_id >>> 32));
        hash = 67 * hash + (int) (this.product_id ^ (this.product_id >>> 32));
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
        final Order_Detail_Key other = (Order_Detail_Key) obj;
        if (this.order_id != other.order_id) {
            return false;
        }
        return this.product_id == other.product_id;
    }

}
