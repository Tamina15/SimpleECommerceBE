/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import rookiesspring.dto.CategoryDTO;
import rookiesspring.dto.response.CategoryResponseDTO;
import rookiesspring.dto.response.custom.CategoryResponseDTOShort;
import rookiesspring.dto.update.CategoryUpdateDTO;
import rookiesspring.mapper.CategoryMapper;
import rookiesspring.model.Category;
import rookiesspring.model.Product;
import rookiesspring.model.composite_model.ProductCategory;
import rookiesspring.repository.CategoryRepository;
import rookiesspring.repository.ProductCategoryRepository;
import rookiesspring.repository.ProductRepository;
import rookiesspring.service.interfaces.CategoryServiceInterface;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service

public class CategoryService implements CategoryServiceInterface {

    CategoryRepository repository;
    CategoryMapper mapper;
    
    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CategoryResponseDTOShort> findAll(String name) {
        return repository.findAllProjectedByNameContainsIgnoreCase(name);
    }

    public CategoryResponseDTO findById(long id) {
        return mapper.ToResponseDTO(repository.findById(id).orElseThrow(() -> new EntityNotFoundException()));
    }

    public CategoryResponseDTO save(CategoryDTO categoryDTO) {
        Category c = mapper.toEntity(categoryDTO);
        repository.save(c);
        return mapper.ToResponseDTO(repository.findById(c.getId()).get());
    }

    public CategoryResponseDTOShort update(CategoryUpdateDTO categoryDTO) {
        System.out.println(categoryDTO.toString());
        Category c = repository.getReferenceById(categoryDTO.id());
        c.setName(categoryDTO.name());
        c.setDescription(categoryDTO.description());
        c = repository.save(c);
        return mapper.ToResponseDTOShort(c);
    }

    public void delete(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("No Category exists");
        }
    }
}
