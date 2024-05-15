/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author HP
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Supplier extends AuditEntity<Long> {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    
    private String name;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier")
    @JsonManagedReference()
//    @JsonBackReference
    private List<ImportBill> import_bill;
    
    @Embedded
    private Address address;
    
}
