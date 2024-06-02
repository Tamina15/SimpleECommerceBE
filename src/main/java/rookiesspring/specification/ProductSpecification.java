/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.specification;

import jakarta.persistence.criteria.Path;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import rookiesspring.model.Category;
import rookiesspring.model.Product;

/**
 *
 * @author HP
 * @author Tamina
 */
public class ProductSpecification {

    public static Specification<Product> countAllWithCategoryIn(List<Long> category_id) {
        return (root, query, builder) -> {
            final Path<Category> group = root.<Category> get("category").get("category").get("id");
            return group.in(category_id);
//            return builder.in(root.get("category").get("category").get("id")).in(category_id);
        };
    }

    public static Specification<Product> countAllFeatured(boolean feature) {
        return (root, query, builder) -> {
            return builder.equal(root.get("feature"), feature);
        };
    }

}
