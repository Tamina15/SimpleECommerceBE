/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rookiesspring.dto.response.custom.ProductResponseDTOShort;
import rookiesspring.model.Product;

/**
 *
 * @author HP
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @Query(value = "select p from Product p left join fetch p.category left join fetch p.images")
    List<Product> findAll();

    @Query(value = "select p from Product p left join fetch p.category c left join fetch p.images where (p.createdDate between :from and :to)")
    public List<Product> findAll(LocalDateTime from, LocalDateTime to);

    @Query(value = "select p from Product p left join fetch p.category c left join fetch p.images "
            + "where (LOWER(p.name) LIKE concat('%', LOWER(:name), '%')) and "
            + "c in :category_ids and "
            + "(p.createdDate between :from and :to)")
    public List<Product> findAll(@Param(value = "name") String name,
            @Param(value = "category_ids") long[] category_ids,
            @Param(value = "from") LocalDateTime from,
            @Param(value = "to") LocalDateTime to);

    public ProductResponseDTOShort findProjectedById(long id);

    boolean existsById(long id);

    public List<Product> getReferenceByIdIn(long[] product_id);
    
//    @Query(value = "select p from Product p left join fetch p.category left join fetch p.images where p.id = ?1")
//    public Optional<Product> findById(long id);

}
