/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import jakarta.persistence.Entity;
import java.util.Objects;
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
public class Rate extends AuditEntity<Long>{


//
//    private User user;
//
//    private Product product;

    private int rating;

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
