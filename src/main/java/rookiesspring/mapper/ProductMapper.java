/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.dto.response.custom.ProductResponseDTOShort;
import rookiesspring.model.Product;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class ProductMapper implements BaseMapper<Product, ProductDTO, ProductResponseDTO> {

    @Override
    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        return product;
    }

    @Override
    public ProductResponseDTO ToResponseDTO(Product product) {
        ProductResponseDTO product_dto = new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getAmount(), product.getRating(), product.getCategory(), product.getImages());
        return product_dto;
    }

    @Override
    public List<ProductResponseDTO> ToResponseDTOList(List<Product> products) {
        List<ProductResponseDTO> list = new ArrayList<>();
        for (Product p : products) {
            ProductResponseDTO d = ToResponseDTO(p);
            list.add(d);
        }
        return list;
    }

    public ProductResponseDTOShort ToResponseDTOShort(Product product) {
        ProductResponseDTOShort product_dto_short = new ProductResponseDTOShort(product.getId(), product.getName(), product.getPrice(), product.getAmount(), product.getRating());
        return product_dto_short;
    }

    public List<ProductResponseDTOShort> ToResponseDTOShortList(Set<Product> products) {
        List<ProductResponseDTOShort> list = new ArrayList<>();
        for (Product p : products) {
            ProductResponseDTOShort d = ToResponseDTOShort(p);
            list.add(d);
        }
        return list;
    }

}
