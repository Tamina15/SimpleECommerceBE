/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model.composite_model;

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
import rookiesspring.model.Order;
import rookiesspring.model.Product;

/**
 *
 * @author HP
 * @author Tamina
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Order_Detail {

    @EmbeddedId
    private Order_Detail_Key id;

    @ManyToOne
    @MapsId("order_id")
    @JsonBackReference
    Order order;

    @ManyToOne
    @MapsId("product_id")
    @JsonBackReference
    Product product;

    private int amount;

    public Order_Detail(Order order, Product product) {
        this.order = order;
        this.product = product;
        this.id = new Order_Detail_Key(order.getId(), product.getId());
    }

    public Order_Detail(Order order, Product product, int amount) {
        this.order = order;
        this.product = product;
        this.amount = amount;
        this.id = new Order_Detail_Key(order.getId(), product.getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.product);
        hash = 17 * hash + Objects.hashCode(this.order);
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
        final Order_Detail other = (Order_Detail) obj;
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return Objects.equals(this.order, other.order);
    }

}
