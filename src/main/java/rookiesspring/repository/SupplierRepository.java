/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rookiesspring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rookiesspring.dto.response.custom.SupplierResponseDTOShort;
import rookiesspring.model.Supplier;

/**
 *
 * @author HP
 */
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    public List<SupplierResponseDTOShort> findAllProjectedBy();

    public SupplierResponseDTOShort findOneProjectedById(long id);

    public List<Supplier> findAllByNameContainingIgnoreCase(String name);

    public List<SupplierResponseDTOShort> findAllProjectedByNameContainingIgnoreCase(String name);

    boolean existsById(long id);

}
