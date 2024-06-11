/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.specification;

import jakarta.persistence.criteria.Path;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;
import rookiesspring.model.Role;
import rookiesspring.model.User;

/**
 *
 * @author HP
 * @author Tamina
 */
public class UserSpecification {

    public static Specification<User> usernameLike(String username) {
        return (root, query, builder) -> {
            return builder.like(root.get("username"), "%" + username + "%");
        };
    }

    public static Specification<User> emailLike(String email) {
        return (root, query, builder) -> {
            return builder.equal(root.get("email"), email);
        };
    }

    public static Specification<User> isBlocked(boolean blocked) {
        return (root, query, builder) -> {
            return builder.equal(root.get("blocked"), blocked);
        };
    }

    public static Specification<User> createdDateInBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, builder) -> {
            return builder.between(root.get("createdDate"), from, to);
        };
    }
    
    public static Specification<User> withRoles(Set<String> roles) {
        return (root, query, builder) -> {
            if(roles.isEmpty()){
                return builder.conjunction();
            }
            final Path<Role> role = root.<Role>get("roles");
            return role.in(roles);
        };
    }
    
    public static Specification<User> filterSpecs(String username, String email, boolean blocked, LocalDateTime from, LocalDateTime to) {
        return usernameLike(username).and(emailLike(email)).and(isBlocked(blocked)).and(createdDateInBetween(from, to));
    }
    
    
}
