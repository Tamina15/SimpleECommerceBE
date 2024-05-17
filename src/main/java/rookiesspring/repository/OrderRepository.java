/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rookiesspring.dto.response.custom.OrderResponseDTOShort;
import rookiesspring.model.Order;

/**
 *
 * @author HP
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findAllByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

//    @Query(value= "SELECT o from Order o join fetch user u")
    public List<OrderResponseDTOShort> findAllProjectedByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    public Optional<OrderResponseDTOShort> findOneProjectedById(long id);

    boolean existsById(long id);
}
