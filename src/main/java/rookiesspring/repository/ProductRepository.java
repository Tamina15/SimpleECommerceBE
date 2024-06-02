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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rookiesspring.dto.response.custom.ProductResponseDTOShort;
import rookiesspring.model.Product;

/**
 *
 * @author HP
 */
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

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

    @Query(value = "select p.id from Product p where (LOWER(p.name) LIKE concat('%', LOWER(:name), '%')) and (p.createdDate between :from and :to) and feature = true")
    public List<Long> findAllFeaturedProductId(@Param(value = "name") String name, @Param(value = "from") LocalDateTime from, @Param(value = "to") LocalDateTime to, Pageable pageable);

    @Query(value = "select p.id from Product p where (LOWER(p.name) LIKE concat('%', LOWER(:name), '%')) and (p.createdDate between :from and :to)")
    public List<Long> findAllProductId(@Param(value = "name") String name, @Param(value = "from") LocalDateTime from, @Param(value = "to") LocalDateTime to, Pageable pageable);

    @Query(value = "select p from Product p left join fetch p.category c left join fetch p.images where p.id in :product_id and c.category.id in :category_id")
    public List<Product> findAllWithCategoryAndImage(@Param(value = "product_id") List<Long> product_id, @Param(value = "category_id") long[] category_id);

    public ProductResponseDTOShort findProjectedById(long id);

    public List<Product> getReferenceByIdIn(long[] product_id);

    public int[] findAllPriceByIdIn(long[] id);
//    @Query(value = "select p from Product p left join fetch p.category left join fetch p.images where p.id = ?1")
//    public Optional<Product> findById(long id);

    
}
