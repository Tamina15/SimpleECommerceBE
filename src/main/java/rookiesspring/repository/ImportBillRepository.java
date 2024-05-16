/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rookiesspring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rookiesspring.model.ImportBill;

/**
 *
 * @author HP
 */
public interface ImportBillRepository extends JpaRepository<ImportBill, Long> {


    boolean existsById(long id);

    public List<ImportBill> findAllByNameContainingIgnoreCase(String name);

}
