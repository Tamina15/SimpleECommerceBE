/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rookiesspring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rookiesspring.model.composite_model.Order_Detail;
import rookiesspring.model.composite_model.Order_Detail_Key;

/**
 *
 * @author HP
 */
public interface OrderDetailRepository extends JpaRepository<Order_Detail, Order_Detail_Key> {
    public List<Order_Detail> findAllByOrderId(long id);
}
