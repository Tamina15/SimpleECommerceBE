/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "delete from order_detail where order_id = ?1 and product_id = ?2", nativeQuery = true)
    @Modifying
    void removeProduct(long order_id, long product_id);

    @Query(value = "update order_detail set amount = amount + ?3 where order_id = ?1 and product_id = ?2", nativeQuery = true)
    @Modifying
    void updateProduct(long order_id, long product_id, int amount);

    @Query(value = "insert into order_detail (order_id, product_id, amount) values (?1, ?2, ?3)", nativeQuery = true)
    @Modifying
    void addProduct(long order_id, long product_id, int amount);

    public List<OrderResponseDTOShort> findAllProjectedByCreatedDateBetweenAndUserIdEquals(LocalDateTime from, LocalDateTime to, long user_id);

    public List<Order> findAllByCreatedDateBetweenAndUserIdEquals(LocalDateTime from, LocalDateTime to, long user_id);

    public Optional<OrderResponseDTOShort> findOneProjectedByIdAndUserIdEquals(long order_id, long user_id);

    public Optional<Order> findByIdAndUserIdEquals(long user_id, long order_id);

}
