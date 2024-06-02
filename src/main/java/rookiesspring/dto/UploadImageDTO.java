/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 * @author Tamina
 */
@Data
public class UploadImageDTO {

    @Positive
    private long product_id;
    private String name = UUID.randomUUID().toString();
    @NotNull(message = "File must not be null")
    private MultipartFile file;

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

}
