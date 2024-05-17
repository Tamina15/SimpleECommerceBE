/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author HP
 * @author Tamina
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
public class UserDetail {

    @Id
    private long id;

    private String firstname;
    private String lastname;
    private boolean gender;
    private int age;
    private String phone;

    @Embedded
    private Address address;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

}
