/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookiesspring.dto.CategoryDTO;
import rookiesspring.dto.response.CategoryResponseDTO;
import rookiesspring.dto.response.custom.CategoryResponseDTOShort;
import rookiesspring.dto.response.custom.ProductResponseDTOShort;
import rookiesspring.model.Category;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class CategoryMapper implements BaseMapper<Category, CategoryDTO, CategoryResponseDTO> {

    ProductMapper productMapper;

    @Override
    public Category toEntity(CategoryDTO dto) {
        Category c = new Category();
        c.setName(dto.name());
        c.setDescription(dto.description());
        return c;
    }

    @Override
    public CategoryResponseDTO ToResponseDTO(Category e) {
        List<ProductResponseDTOShort> p = new ArrayList<>();
        if (e.getProducts() != null) {
            p.addAll(productMapper.ToResponseDTOShortList(e.getProducts()));
        }
        CategoryResponseDTO c = new CategoryResponseDTO(e.getId(), e.getName(), e.getDescription(), p);
        return c;
    }

    @Override
    public List<CategoryResponseDTO> ToResponseDTOList(List<Category> e) {
        List<CategoryResponseDTO> list = new ArrayList<>();
        for (Category c : e) {
            list.add(ToResponseDTO(c));
        }
        return list;
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CategoryResponseDTOShort ToResponseDTOShort(Category c) {
        CategoryResponseDTOShort dto = new CategoryResponseDTOShort(c.getId(), c.getName(), c.getDescription());
        return dto;
    }

}
