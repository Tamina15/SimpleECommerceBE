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
@EqualsAndHashCode
public class Order_Detail {
 
    @EmbeddedId
    private Order_Detail_Key id;
    
    @ManyToOne
    @MapsId("product_id")
    @JsonBackReference
    Product product;

    @ManyToOne
    @MapsId("order_id")
    @JsonBackReference
    Order order;
    
    private int amount;

    public Order_Detail(Product product, Order order) {
        this.product = product;
        this.order = order;
        this.id = new Order_Detail_Key(product.getId(), order.getId());
    }

}
