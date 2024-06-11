/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.specification;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import rookiesspring.dto.ProductRequestDTO;
import rookiesspring.model.Category;
import rookiesspring.model.Product;

/**
 *
 * @author HP
 * @author Tamina
 */
public class ProductSpecification {

    private ProductRequestDTO filter;

    public static Specification<Product> withCategoryIn(List<Long> category_id) {
        return (root, query, builder) -> {
            if (category_id.isEmpty()) {
                return builder.conjunction();
            }
            final Path<Category> group = root.<Category>get("category").get("category").get("id");
            return group.in(category_id);
//            return builder.in(root.get("category").get("category").get("id")).in(category_id);
        };
    }

    public static Specification<Product> isFeatured(boolean feature) {
        return (root, query, builder) -> {
            return builder.equal(root.get("feature"), feature);
        };
    }

    public static Specification<Product> isDeleted(boolean deleted) {
        return (root, query, builder) -> {
            return builder.equal(root.get("deleted"), deleted);
        };
    }

    public static Specification<Product> createdDateInBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, builder) -> {
            return builder.between(root.get("createdDate"), from, to);
        };
    }

    public static Specification<Product> nameLike(String name) {
        return (root, query, builder) -> {
            return builder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Product> fetchImages() {
        return (root, query, builder) -> {
            if (Long.class != query.getResultType()) {
                root.fetch("images", JoinType.LEFT);
            }
            return builder.conjunction();
        };
    }

    public static Specification<Product> filterSpecs(List<Long> category_id, String name, boolean featured, boolean deleted, LocalDateTime from, LocalDateTime to) {
        return withCategoryIn(category_id).and(nameLike(name)).and(isFeatured(featured)).and(isDeleted(deleted)).and(createdDateInBetween(from, to));
    }

    public static Specification<Product> filterSpecsJoinImages(List<Long> category_id, String name, boolean featured, boolean deleted, LocalDateTime from, LocalDateTime to) {
        return Specification.where(fetchImages()).and(withCategoryIn(category_id)).and(nameLike(name)).and(isFeatured(featured)).and(isDeleted(deleted)).and(createdDateInBetween(from, to));
    }

}
