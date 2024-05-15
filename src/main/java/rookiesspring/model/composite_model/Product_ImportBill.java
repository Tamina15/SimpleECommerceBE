/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model.composite_model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import rookiesspring.model.ImportBill;
import rookiesspring.model.Product;

/**
 *
 * @author HP
 * @author Tamina
 */
@Entity
@Table(name = "product_importbill")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Product_ImportBill {

    @EmbeddedId
    Product_ImportBill_Key id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product_id")
//    @JoinColumn(name = "product_id")
    @JsonBackReference
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("importbill_id")
//    @JoinColumn(name = "importbill_id")
    @JsonBackReference
    ImportBill importbill;
    
    @ColumnDefault("1")
    private int amount;
    
    public Product_ImportBill(Product product, ImportBill importbill) {
        this.product = product;
        this.importbill = importbill;
        this.id = new Product_ImportBill_Key(product.getId(), importbill.getId());
    }
}
