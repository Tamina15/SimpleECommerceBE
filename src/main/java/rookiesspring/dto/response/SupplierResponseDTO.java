/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto.response;

import java.util.List;
import rookiesspring.model.Address;
import rookiesspring.model.ImportBill;

/**
 *
 * @author HP
 * @author Tamina
 */
public record SupplierResponseDTO(long id, String name, List<ImportBill> import_bill, Address address) {

}
