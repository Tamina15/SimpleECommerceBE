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
@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Rate extends AuditEntity<Long> {

    @ManyToOne(cascade = CascadeType.MERGE)  // field name inside db
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)  // field name inside db
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Product product;

    private int rate;
    
    private String comment;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.getId() ^ (this.getId() >>> 32));
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
        final Rate other = (Rate) obj;
        return Objects.equals(this.getId(), other.getId());
    }

}
