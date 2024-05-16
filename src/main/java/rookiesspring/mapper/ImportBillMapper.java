/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import rookiesspring.dto.ImportBillDTO;
import rookiesspring.dto.response.ImportBillResponseDTO;
import rookiesspring.dto.response.custom.ProductResponseDTOShort;
import rookiesspring.dto.response.custom.SupplierResponseDTOShort;
import rookiesspring.model.ImportBill;
import rookiesspring.model.Product;
import rookiesspring.model.Supplier;
import rookiesspring.model.composite_model.Product_ImportBill;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class ImportBillMapper implements BaseMapper<ImportBill, ImportBillDTO, ImportBillResponseDTO> {

    @Override
    public ImportBill toEntity(ImportBillDTO dto) {
        Supplier s = new Supplier();
        s.setId(dto.supplier_id());
        List<Product> product_list = new ArrayList<>();
        for (long l : dto.products_ids()) {
            Product p = new Product();
            p.setId(l);
        }
        ImportBill i = new ImportBill(dto.name(), s, product_list);
        return i;
    }

    @Override
    public ImportBillResponseDTO ToResponseDTO(ImportBill e) {
        ProductResponseDTOShort[] products = new ProductResponseDTOShort[e.getProducts().size()];
        for (int i = 0; i < e.getProducts().size(); i++) {
            Product_ImportBill pi = e.getProducts().get(i);
            Product p = pi.getProduct();
            ProductResponseDTOShort p_dto = new ProductResponseDTOShort(p.getId(), p.getName(), p.getPrice(), p.getAmount(), p.getRating());
            products[i] = p_dto;
        }
        SupplierResponseDTOShort s = new SupplierResponseDTOShort(e.getSupplier().getId(), e.getSupplier().getName(), e.getSupplier().getAddress());
        ImportBillResponseDTO i = new ImportBillResponseDTO(e.getId(), e.getName(), e.getTotalPrice(), e.getImportDate(), e.isProcessed(), s, products);
        return i;
    }

    @Override
    public List<ImportBillResponseDTO> ToResponseDTOList(List<ImportBill> e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
