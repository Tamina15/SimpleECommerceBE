/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model.composite_model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Getter
@Setter
public class Product_ImportBill_Key {

    @Column()
    Long product_id;

    @Column()
    Long importbill_id;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.product_id);
        hash = 97 * hash + Objects.hashCode(this.importbill_id);
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
        final Product_ImportBill_Key other = (Product_ImportBill_Key) obj;
        if (!Objects.equals(this.product_id, other.product_id)) {
            return false;
        }
        return Objects.equals(this.importbill_id, other.importbill_id);
    }
    
    
    
}
