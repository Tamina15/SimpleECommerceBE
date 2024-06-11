/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rookiesspring.model.Product;

/**
 *
 * @author HP
 */
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "select p from Product p left join fetch p.category c left join fetch p.images where p.id in :product_id")
    public List<Product> findAllByProductIdIn(@Param(value = "product_id") List<Long> product_id);

    @Query(value = "select p.id from Product p join p.category c where (LOWER(p.name) LIKE concat('%', LOWER(:name), '%')) and (p.createdDate between :from and :to) and feature = true and c.category.id in :category_id")
    public List<Long> findAllFeaturedProductId(@Param(value = "name") String name, @Param(value = "from") LocalDateTime from, @Param(value = "to") LocalDateTime to, @Param(value = "category_id") long[] category_id, Pageable pageable);

    @Query(value = "select p.id from Product p join p.category c where (LOWER(p.name) LIKE concat('%', LOWER(:name), '%')) and (p.createdDate between :from and :to) and c.category.id in :category_id")
    public List<Long> findAllProductId(@Param(value = "name") String name, @Param(value = "from") LocalDateTime from, @Param(value = "to") LocalDateTime to, @Param(value = "category_id") long[] category_id, Pageable pageable);

    @Query(value = "select p from Product p left join fetch p.category c left join fetch p.images where p.id in :product_id and c.category.id in :category_id")
    public List<Product> findAllWithCategoryAndImage(@Param(value = "product_id") List<Long> product_id, @Param(value = "category_id") long[] category_id);

    public List<Product> getReferenceByIdIn(long[] product_id);

    @Query(value = "update Product p set p.deleted = false where p.id = :product_id")
    @Modifying
    public void restoreProduct(@Param(value = "product_id") long id);

}
