/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rookiesspring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rookiesspring.model.Rate;

/**
 *
 * @author HP
 * @author Tamina
 */

public interface RateRepository extends JpaRepository<Rate, Long>{
    public List<Rate> findAllByProductId(Long id);
    
}
