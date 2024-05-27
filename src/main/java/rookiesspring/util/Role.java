/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.util;

/**
 *
 * @author HP
 * @author Tamina
 */
public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String name;

    private Role(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
