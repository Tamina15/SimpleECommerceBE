/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rookiesspring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import rookiesspring.model.Rate;

/**
 *
 * @author HP
 * @author Tamina
 */

public interface RateRepository extends JpaRepository<Rate, Long>, JpaSpecificationExecutor<Rate>{
    public List<Rate> findAllByProductId(Long id);

    public boolean existsByProductIdAndUserId(Long id, Long id0);
    
}
