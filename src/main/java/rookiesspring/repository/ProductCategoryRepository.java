/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rookiesspring.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rookiesspring.model.Category;
import rookiesspring.model.Product;
import rookiesspring.model.composite_model.ProductCategory;

/**
 *
 * @author HP
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(value = "delete from product_category pc where pc.product_id = ?1 and pc.category_id = ?2", nativeQuery = true)
    @Modifying
    public void deleteByProductAndCategory(long product, long category);

    public void deleteByProductIdAndCategoryId(long product, long category);

    @Query(value = "select pc.id from ProductCategory pc where pc.product = ?1 and pc.category = ?2")
    public Optional<Long> findId(Product p, Category c);

    public ProductCategory getReferenceByProductIdAndCategoryId(long pid, long cid);

    public boolean existsByProductIdAndCategoryId(long product_id, long category_id);
    
    public long[] findAllIdByProductId(long product_id);
}
