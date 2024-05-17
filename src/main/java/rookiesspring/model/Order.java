/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
public class Order extends AuditEntity<Long> {

    @ManyToOne(cascade = CascadeType.ALL)  // field name inside db
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    private String totalPrice;

    @OneToMany(mappedBy = "order")
    private List<Order_Detail> details;

}
