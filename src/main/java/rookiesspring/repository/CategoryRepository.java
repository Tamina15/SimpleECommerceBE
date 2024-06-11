/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rookiesspring.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rookiesspring.dto.response.custom.CategoryResponseDTOShort;
import rookiesspring.model.Category;

/**
 *
 * @author HP
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @Query(value = "select c from Category c left join fetch c.products")
    List<Category> findAll();

//    @Query(value = "select c from Category c left join c.products p left join fetch p.products where c.id = ?1")
//    Optional<Category> findId(long id);

    List<CategoryResponseDTOShort> findAllProjectedBy();

    Optional<CategoryResponseDTOShort> findOneProjectedById(long id);

    List<Category> findAllByNameContainsIgnoreCase(String name);

    public List<CategoryResponseDTOShort> findAllProjectedByNameContainsIgnoreCase(String name);

    @Query(value = "select c.id from Category c")
    public long[] getAllId();

//    @Query(value = "insert into product_category (product_id, category_id) values (?2, ?1)", nativeQuery = true)
//    @Modifying
//    public void insertProduct(long category_id, long product_id);

}
