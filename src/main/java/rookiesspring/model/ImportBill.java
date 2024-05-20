/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import rookiesspring.model.composite_model.Product_ImportBill;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "import_bill")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE import_bill SET deleted = true WHERE id=?")
@SQLRestriction(value = "deleted=false")
public class ImportBill extends AuditEntity<Long>{

    private String name;

    @Column(updatable = false)
    @ColumnDefault(value = "0")
    private double totalPrice =0;

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private boolean processed;

    @ManyToOne  // field name inside db
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Supplier supplier;

    @OneToMany(mappedBy = "importbill")
    @JsonManagedReference
    private List<Product_ImportBill> products = new ArrayList<>();

    public ImportBill(String name, Supplier supplier) {
        this.name = name;
        this.supplier = supplier;
    }

    // https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/
    public void addProduct(Product p, int amount) {
        Product_ImportBill pi = new Product_ImportBill(p, this);
        pi.setAmount(amount);
        products.add(pi);
    }

    public void removeProduct(Product product) {
        Iterator<Product_ImportBill> iterator = this.products.iterator();
        while (iterator.hasNext()) {
            Product_ImportBill pi = iterator.next();

            if (pi.getImportbill().equals(this)
                    && pi.getProduct().equals(product)) {
                iterator.remove();
                pi.getImportbill().getProducts().remove(pi);
                pi.setImportbill(null);
                pi.setProduct(null);
            }
        }
    }

    @Override
    public String toString() {
        return "ImportBill{" + "id=" + getId() + ", name=" + name + ", totalPrice=" + totalPrice + ", processed=" + processed + ", supplier=" + supplier.getId() + ", products=" + products.size() + '}';
    }

}
