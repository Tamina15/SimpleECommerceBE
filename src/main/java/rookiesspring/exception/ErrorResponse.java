/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

/**
 *
 * @author HP
 * @author Tamina
 */

@Value
@Builder
@JsonInclude(Include.NON_EMPTY)
@ToString
/**
 * @author https://github.com/phulecse2420/demo/blob/master/src/main/java/com/example/demo/dto/response/ErrorResponse.java
 */
public class ErrorResponse {

    private Integer code;
    private String message;
    private Object errors;

}
