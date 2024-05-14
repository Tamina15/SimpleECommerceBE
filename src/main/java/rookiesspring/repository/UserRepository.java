/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rookiesspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rookiesspring.model.User;

/**
 *
 * @author HP
 * @author Tamina
 */

public interface UserRepository extends JpaRepository<User, Long>{

}
