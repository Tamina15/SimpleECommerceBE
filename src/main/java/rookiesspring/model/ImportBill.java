/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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
//@ToString
public class ImportBill extends AuditEntity<Long>{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String name;

    @Column(updatable = false)
    @ColumnDefault(value = "0")
    private double totalPrice =0;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime importDate = LocalDateTime.now();

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private boolean processed;

    @ManyToOne  // field name inside db
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
//    @JsonManagedReference
    private Supplier supplier;

    @OneToMany(mappedBy = "importbill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Product_ImportBill> products = new ArrayList<>();

    @Transient
    transient List<Product> p;

    public ImportBill(String name, Supplier supplier, List<Product> p) {
        this.name = name;
        this.supplier = supplier;
        this.p = p;
    }

    // https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/
    public void addProduct(Product p, int amount) {
        Product_ImportBill pi = new Product_ImportBill(p, this);
        pi.setAmount(amount);
        products.add(pi);
//        p.getImport_bill().add(pi);
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
//
//    @Override
//    public String toString() {
//        String s = "";
//        s += name + " ";
//        if (products == null) {
//            System.out.println("product null");
//        }
//        for (Product_ImportBill pi : products) {
//            if (pi != null) {
//                Product p = pi.getProduct();
//                ImportBill i = pi.getImportbill();
//                if (p == null) {
//                    System.out.println("product null ");
//                } else {
//                    System.out.print(p.getId() + " ");
//                }
//                if (i == null) {
//                    System.out.println("import null ");
//                } else {
//                    System.out.print(i.getId() + " ");
//                }
//            } else {
//                System.out.println("Product_ImportBill null ");
//            }
//        }
//        return s;
//    }

    @Override
    public String toString() {
        return "ImportBill{" + "id=" + getId() + ", name=" + name + ", totalPrice=" + totalPrice + ", importDate=" + importDate + ", processed=" + processed + ", supplier=" + supplier.getId() + ", products=" + products.size() + ", p=" + p + '}';
    }

}
