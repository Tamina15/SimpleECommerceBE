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
import java.time.LocalDateTime;
import lombok.AccessLevel;
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
@NoArgsConstructor
public class UserDetail extends AuditEntity<Long> {
    
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

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public boolean isGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public User getUser() {
        return user;
    }
    
}
