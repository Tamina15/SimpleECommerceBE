/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rookiesspring.dto.response.custom.UserResponseDTOShort;
import rookiesspring.model.User;

/**
 *
 * @author HP
 * @author Tamina
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query(value = "select u from User u left join fetch u.orders left join fetch u.user_detail where (LOWER(u.username) LIKE concat('%', LOWER(:username), '%')) ")
    List<User> findAll(@Param(value = "username") String username);

    @Override
    @Query(value = "select u from User u left join fetch u.orders left join fetch u.user_detail")
    List<User> findAll();

    List<User> findAllByUsername(String username);

    public List<UserResponseDTOShort> findAllProjectedBy();

    public List<UserResponseDTOShort> findAllProjectedBy(Pageable pageable);

    public Optional<UserResponseDTOShort> findProjectedById(Long userId);

    public List<UserResponseDTOShort> findAllProjectedByUsernameContainsIgnoreCase(String username);

    public boolean existsByEmail(String email);

    public boolean existsByUsername(String username);

    public Optional<User> findByUsername(String username);

    public Optional<User> findByEmail(String email);

    public Optional<UserResponseDTOShort> findProjectedByEmail(String email);

    @Query(value = "update User u set u.deleted = true where u.id = ?1")
    @Modifying
    public void softDeleteById(@Param(value = "id") long user_id);
    
    @Query(value = "update User u set u.deleted = false where u.id = ?1")
    @Modifying
    public void restoreUser(@Param(value = "id") Long user_id);
}
