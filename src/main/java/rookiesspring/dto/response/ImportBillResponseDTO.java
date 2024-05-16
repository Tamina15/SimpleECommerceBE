/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto.response;

import java.time.LocalDateTime;
import rookiesspring.dto.response.custom.ProductResponseDTOShort;
import rookiesspring.dto.response.custom.SupplierResponseDTOShort;

/**
 *
 * @author HP
 * @author Tamina
 */
//public record ImportBillResponseDTO(long id, String name, double total_price, LocalDateTime import_date, boolean processed, SupplierResponseDTO supplier, Product[] products) {
public record ImportBillResponseDTO(long id, String name, double total_price, LocalDateTime import_date, boolean processed, SupplierResponseDTOShort supplier, ProductResponseDTOShort[] products) {

}
