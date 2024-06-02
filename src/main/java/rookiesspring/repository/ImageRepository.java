/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rookiesspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rookiesspring.model.Image;

/**
 *
 * @author HP
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

    public boolean existsByName(String name);
    
    @Query(value = "delete from Image i where i.id = :id")
    @Modifying()
    public void hardDelete(@Param(value = "id")long id);
}
