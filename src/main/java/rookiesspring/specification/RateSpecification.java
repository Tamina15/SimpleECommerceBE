/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rookiesspring.specification;

import org.springframework.data.jpa.domain.Specification;
import rookiesspring.model.Rate;

/**
 *
 * @author HP
 * @author Tamina
 */

public class RateSpecification {
    public static Specification<Rate> countAllRating(long product_id) {
        return (root, query, builder) -> {
            return builder.equal(root.get("product").get("id"), product_id);
        };
    }
}
