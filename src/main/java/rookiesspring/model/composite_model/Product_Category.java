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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rookiesspring.model.Category;
import rookiesspring.model.Product;

/**
 *
 * @author HP
 * @author Tamina
 */
//@Entity
@NoArgsConstructor
@Getter
@Setter
@Deprecated
public class Product_Category {

    @EmbeddedId
    Product_Category_Key id;

    @ManyToOne
    @MapsId("product_id")
//    @JoinColumn(name = "product_id")
    @JsonBackReference
    Product product;

    @ManyToOne
    @MapsId("category_id")
//    @JoinColumn(name = "category_id")
    @JsonBackReference
    Category category;

    public Product_Category(Product product, Category category) {
        this.product = product;
        this.category = category;
        this.id = new Product_Category_Key(product.getId(), category.getId());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.product);
        hash = 17 * hash + Objects.hashCode(this.category);
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
        final Product_Category other = (Product_Category) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return Objects.equals(this.category, other.category);
    }

}
