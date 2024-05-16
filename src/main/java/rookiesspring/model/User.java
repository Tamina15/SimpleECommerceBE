/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author HP
 * @author Tamina
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "`user`")
public class User extends AuditEntity<Long>{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(unique = true)
    private String email;
    private String username;
    private String password;
    private String refreshToken;
    private boolean isBlock = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference()
    private List<Order> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    UserDetail user_detail;

    @Override
    public String toString() {
        return "User{" + "id=" + getId() + ", email=" + email + ", password=" + password + ", refreshToken=" + refreshToken + ", isBlock=" + isBlock + ", orders=" + orders.size() + ", user_detail=" + user_detail + '}';
    }

}
