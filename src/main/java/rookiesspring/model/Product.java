/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import rookiesspring.model.composite_model.ProductCategory;

/**
 *
 * @author HP
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
//@ToString
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id=?")
//@SQLRestriction(value = "deleted=false")
public class Product extends AuditEntity<Long> {

    private String name;

    @Column(length = 1000)
    private String description;

    private double price;

    @ColumnDefault(value = "false")
    private boolean feature;

    @ColumnDefault(value = "0")
    private int amount;

    @ColumnDefault(value = "0")
    private double rating;
    
    @ColumnDefault(value = "0")
    private long total_count_rating;
//    @ManyToMany()
//    @JoinTable(name = "product_category",
//            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
//    @JsonManagedReference()
//    private Set<Category> category;
    @OneToMany(mappedBy = "product")
    @JsonManagedReference()
    private Set<ProductCategory> category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference()
    private Set<Image> images;

    public Product(long id) {
        this.setId(id);
    }

    public boolean checkAmount() {
        return amount > 0;
    }

    public boolean checkAmount(int a) {
        return a > 0 && amount >= a;
    }

    public boolean addCategory(Category c) {
        return category.add(new ProductCategory(this, c));
    }

    public boolean addCategory(ProductCategory pc) {
        return category.add(pc);
    }

    public boolean addImage(Image i) {
        return images.add(i);
    }

    public boolean removeCategory(Category c) {
        return category.remove(new ProductCategory(this, c));
    }

    public boolean removeImage(Image i) {
        return images.remove(i);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(getId());
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
        final Product other = (Product) obj;
        return Objects.equals(getId(), other.getId());
    }

    public void reduceAmount(int amount) {
        this.amount -= amount;
    }

    @Override
    public String toString() {
        return "Product{" + "name=" + name + ", description=" + description + ", price=" + price + ", feature=" + feature + ", amount=" + amount + ", rating=" + rating + ", total_count_rating=" + total_count_rating + '}';
    }

}
