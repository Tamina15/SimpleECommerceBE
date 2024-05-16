/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

//    @Query(value = "select p from Product p left join fetch Rate r on p.id = r.id")
//    List<Product> findAllRating();

//    @Override
//    @Query(value = "select p from Product p left join fetch p.category left join fetch p.category.category left join p.images")
//    List<Product> findAll();

    List<ProductResponseDTOShort> findAllProjectedBy();

    public List<Product> findAllByName(String name);

    public ProductResponseDTOShort findProjectedById(long id);

    boolean existsById(long id);

//    @Query(value = "select p from Product p join p.category join p.category.category c where c.id in ?1")
//    public List<Product> findAllByCategory(long[] category_id);

    public List<ProductResponseDTOShort> findAllProjectedByCategoryIn(long[] category_id);

    public List<Product> getReferenceByIdIn(long[] product_id);    
}
