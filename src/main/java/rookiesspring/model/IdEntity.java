/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class IdEntity<P extends Serializable> implements Persistable<P> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private P id;

    @Transient
    @Override
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final IdEntity<?> other = (IdEntity<?>) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
