/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 *
 * @author HP
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE supplier SET deleted = true WHERE id=?")
@SQLRestriction(value = "deleted=false")
public class Supplier extends AuditEntity<Long> {
    
    private String name;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier")
    @JsonManagedReference()
    private List<ImportBill> import_bill;
    
    @Embedded
    private Address address;
    
}
