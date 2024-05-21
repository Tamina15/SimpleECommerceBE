/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rookiesspring.dto.response.custom.UserResponseDTOShort;
import rookiesspring.model.User;

/**
 *
 * @author HP
 * @author Tamina
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u left join fetch u.orders left join fetch u.user_detail where (LOWER(u.username) LIKE concat('%', LOWER(:username), '%')) ")
    List<User> findAll(@Param(value = "username")String username);

    @Override
    @Query(value = "select u from User u left join fetch u.orders left join fetch u.user_detail")
    List<User> findAll();

    List<User> findAllByUsername(String username);

    public List<UserResponseDTOShort> findAllProjectedBy();

    public Optional<UserResponseDTOShort> findProjectedById(Long userId);

    public List<UserResponseDTOShort> findAllProjectedByUsernameContainsIgnoreCase(String username);

    public boolean existsByEmail(String email);
}
