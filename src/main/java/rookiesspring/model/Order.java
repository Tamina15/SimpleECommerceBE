/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import rookiesspring.model.composite_model.Order_Detail;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "`order`")
@NoArgsConstructor
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE 'order' SET deleted = true WHERE id=?")
@SQLRestriction(value = "deleted=false")
public class Order extends AuditEntity<Long> {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // field name inside db
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    private String totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order_Detail> details;

    /**
     * @param product
     * @param amount
     * @see
     * https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/
     */
    public void addProduct(Product product, int amount) {
        Order_Detail od = new Order_Detail(this, product);
        od.setAmount(amount);
        if (details.contains(od)) {
            details.remove(od);
        }
        details.add(od);
    }

    public void addProduct(Order_Detail od, int amount) {
        od.setAmount(amount);
        details.add(od);
    }

    public void removeProduct(Product product) {
        Order_Detail od = new Order_Detail(this, product);
        details.remove(od);
    }
}
