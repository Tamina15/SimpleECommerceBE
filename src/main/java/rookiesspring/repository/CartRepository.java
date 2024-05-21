/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rookiesspring.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rookiesspring.model.Cart;

/**
 *
 * @author HP
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

    public List<Cart> findAllByUserIdEquals(long user_id);

    public Optional<Cart> findOneByUserIdEqualsAndProductIdEquals(long user_id, long product_id);

    public boolean existsByUserIdAndProductId(long user_id, long product_id);

    public void deleteByUserId(long user_id);
}
