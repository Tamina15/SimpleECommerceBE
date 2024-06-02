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
public class ProductRequestDTO {

    private String name = "";
    private long[] category_id = new long[0];

    private LocalDateTime from = Util.minDateTime;
    private LocalDateTime to = LocalDateTime.now();
    private int limit = 12;
    @PositiveOrZero()
    private int page = 0;
    private boolean featured = false;
    private String sortBy = "rating";
    private String order = "ASC";

    public void setOrder(String order) {
        if (order.equals("DESC")) {
            this.order = order;
        }
    }

    public void setFrom(LocalDateTime from) {
        this.from = from == null ? Util.minDateTime : from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to == null ? LocalDateTime.now() : to;
    }

}
