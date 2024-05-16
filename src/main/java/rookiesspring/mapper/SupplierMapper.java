/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import rookiesspring.dto.SupplierDTO;
import rookiesspring.dto.response.SupplierResponseDTO;
import rookiesspring.model.Supplier;

/**
 *
 * @author HP
 * @author Tamina
 */

@Service
public class SupplierMapper implements BaseMapper<Supplier, SupplierDTO, SupplierResponseDTO> {

    @Override
    public Supplier toEntity(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        return supplier;
    }

    @Override
    public SupplierResponseDTO ToResponseDTO(Supplier supplier) {
        SupplierResponseDTO suppplier_dto = new SupplierResponseDTO(supplier.getId(), supplier.getName(), supplier.getImport_bill(), supplier.getAddress());
        return suppplier_dto;
    }

    @Override
    public List<SupplierResponseDTO> ToResponseDTOList(List<Supplier> suppliers) {
        List<SupplierResponseDTO> list = new ArrayList<>();
        for(Supplier s : suppliers){
            SupplierResponseDTO d = ToResponseDTO(s);
            list.add(d);
        }
        return list;
    }

}
