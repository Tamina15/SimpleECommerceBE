/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.specification;

import org.springframework.data.jpa.domain.Specification;
import rookiesspring.model.Category;

/**
 *
 * @author HP
 * @author Tamina
 */
public class CategorySpecification {

    public static Specification<Category> nameContains(String name) {
        return (root, query, builder) -> {
            return builder.like(root.get("name"), "%" + name + "%");
        };
    }
    public static Specification<Category> descriptionContains(String description) {
        return (root, query, builder) -> {
            return builder.like(root.get("name"), "%" + description + "%");
        };
    }

    public static Specification<Category> filterSpecs(String name, String description) {
        return Specification.where(nameContains(name)).and(descriptionContains(description));
    }

}
