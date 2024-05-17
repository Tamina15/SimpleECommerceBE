/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rookiesspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rookiesspring.model.Image;

/**
 *
 * @author HP
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
public boolean existsByName(String name);
}
