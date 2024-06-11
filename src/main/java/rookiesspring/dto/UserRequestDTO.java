/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto;

import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.Data;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 * @author Tamina
 */
@Data
public class UserRequestDTO {

    private String username = "";
    private String email = "";
    private boolean blocked = false; 

    private LocalDateTime from = Util.minDateTime;
    private LocalDateTime to = LocalDateTime.now();
    
    private int limit = 12;
    @PositiveOrZero()
    private int page = 0;

    private String sortBy = "id";
    private String orderBy = "ASC";

    public void setUsername(String username) {
        if (username != null) {
            this.username = username;
        }
    }
    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        }
    }

    public void setOrderBy(String orderBy) {
        if (orderBy.equals("DESC")) {
            this.orderBy = orderBy;
        }
    }

    public void setFrom(LocalDateTime from) {
        this.from = from == null ? Util.minDateTime : from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to == null ? LocalDateTime.now() : to;
    }
}
