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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@SQLDelete(sql = "UPDATE \"user\" SET deleted = true WHERE id=?")
//@SQLRestriction(value = "deleted = false")
public class User extends AuditEntity<Long> implements UserDetails {

    @Column(unique = true)
    private String email;

    private String username;
    private String password;
    private String refreshToken;
    private boolean isBlock = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference()
    private List<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference()
    private List<Rate> rates;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @PrimaryKeyJoinColumn
    UserDetail user_detail;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Cart> carts;

    private Set<String> roles;

    public User(long id) {
        setId(id);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + getId() + ", email=" + email + ", password=" + password + ", refreshToken=" + refreshToken + ", isBlock=" + isBlock + ", orders=" + orders.size() + ", user_detail=" + user_detail + '}';
    }

    // TODO
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> auth = new HashSet<>();
        for (String s : roles) {
            auth.add(new SimpleGrantedAuthority(s));
        }
        return auth;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    /**
     * Return Email, Use for Spring Security
     */
    public String getUsername() {
        return email;
    }

    /**
     * Return Username
     *
     * @return
     */
    public String getUserName() {
        return username;
    }
}