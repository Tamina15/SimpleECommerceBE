/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookiesspring.dto.CategoryDTO;
import rookiesspring.dto.response.CategoryResponseDTO;
import rookiesspring.dto.response.custom.CategoryResponseDTOShort;
import rookiesspring.dto.update.CategoryUpdateDTO;
import rookiesspring.mapper.CategoryMapper;
import rookiesspring.model.Category;
import rookiesspring.model.Product;
import rookiesspring.repository.CategoryRepository;
import rookiesspring.repository.ProductRepository;
import rookiesspring.service.interfaces.CategoryServiceInterface;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service

public class CategoryService implements CategoryServiceInterface {

    CategoryRepository repository;
    CategoryMapper mapper;
    ProductRepository productRepository;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CategoryResponseDTOShort> findAll(String name) {
        return repository.findAllProjectedByNameContainsIgnoreCase(name);
    }

    public CategoryResponseDTOShort findById(long id) {
        return repository.findOneProjectedById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<CategoryResponseDTO> findAllFull(String name) {
        return mapper.ToResponseDTOList(repository.findAllByNameContainsIgnoreCase(name));
    }

    public CategoryResponseDTO findByIdFull(long id) {
        return mapper.ToResponseDTO(repository.findById(id).orElseThrow(() -> new EntityNotFoundException()));
    }

    public CategoryResponseDTO save(CategoryDTO categoryDTO) {
        Category c = mapper.toEntity(categoryDTO);
        c = repository.save(c);
        if (categoryDTO.product_id() != null) {
            addProduct(c.getId(), categoryDTO.product_id());
        }
        return mapper.ToResponseDTO(c);
    }

    public CategoryResponseDTOShort update(CategoryUpdateDTO categoryDTO) {
        Category c = repository.getReferenceById(categoryDTO.id());
        if (categoryDTO.name() != null) {
            c.setName(categoryDTO.name());
        }
        if (categoryDTO.description() != null) {
            c.setDescription(categoryDTO.description());
        }
        c = repository.save(c);
        return mapper.ToResponseDTOShort(c);
    }

    public boolean delete(long id) {
        if (checkExist(id)) {
            Category c = repository.getReferenceById(id);
            for(Product p : c.getProducts()){
                p.removeCategory(c);
            }
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public CategoryResponseDTO addProduct(long category_id, long[] product_ids) {
        Category c = repository.getReferenceById(category_id);
        for (long id : product_ids) {
            Product p = productRepository.getReferenceById(id);
            p.addCategory(c);
            c.addProduct(p);
        }
        repository.save(c);
        return mapper.ToResponseDTO(c);
    }

    public CategoryResponseDTO removeProduct(long category_id, long[] product_ids) {
        Category c = repository.getReferenceById(category_id);
        for (long id : product_ids) {
            Product p = productRepository.getReferenceById(id);
            p.removeCategory(c);
        }
        c = repository.save(c);
        return mapper.ToResponseDTO(c);
    }

    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

}
