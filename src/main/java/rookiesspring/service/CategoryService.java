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
import rookiesspring.model.Rate;
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
    ProductRepository productRepository;
    @Autowired
    ProductCategoryRepository productcategoryRepository;

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

//    public List<CategoryResponseDTO> findAllFull(String name) {
//        return mapper.ToResponseDTOList(repository.findAllByNameContainsIgnoreCase(name));
//    }
//
//    public CategoryResponseDTO findByIdFull(long id) {
//        return mapper.ToResponseDTO(repository.findById(id).orElseThrow(() -> new EntityNotFoundException()));
//    }
    public CategoryResponseDTO save(CategoryDTO categoryDTO) {
        Category c = mapper.toEntity(categoryDTO);
        repository.save(c);
        Set<ProductCategory> set = new HashSet<>();
        if (categoryDTO.product_id() != null) {
            for (long id : categoryDTO.product_id()) {
                Product product = productRepository.getReferenceById(id);
                ProductCategory productCategory = new ProductCategory(product, c);
                set.add(productCategory);
                productcategoryRepository.save(productCategory);
            }
        }
        c.setProducts(set);
        Util.addCategory(c.getId());
        return mapper.ToResponseDTO(repository.findById(c.getId()).get());
    }

    public CategoryResponseDTOShort update(CategoryUpdateDTO categoryDTO) {
        Category c = repository.getReferenceById(categoryDTO.id());
        c.setName(categoryDTO.name());
        c.setDescription(categoryDTO.description());
        c = repository.save(c);
        return mapper.ToResponseDTOShort(c);
    }

    public void delete(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            Util.removeCategory(id);
        } else {
            throw new EntityNotFoundException("No Category exists");
        }

    }
//
//    public CategoryResponseDTO addProduct(long category_id, long[] product_ids) {
//        if (repository.existsById(category_id)) {
//            Category category = repository.getReferenceById(category_id);
//            for (long id : product_ids) {
//                if (productRepository.existsById(id)) {
//                    System.out.println(id);
//                    Product product = productRepository.getReferenceById(id);
//                    ProductCategory product_category = new ProductCategory(product, category);
//                    productcategoryRepository.save(product_category);
//                }
//            }
//            return mapper.ToResponseDTO(repository.findById(category_id).get());
//        } else {
//            throw new EntityNotFoundException();
//        }
//    }
//
//    @Transactional(rollbackOn = RuntimeException.class)
//    public CategoryResponseDTO removeProduct(long category_id, long[] product_ids) {
//        if (repository.existsById(category_id)) {
//            for (long id : product_ids) {
//                if (productRepository.existsById(id)) {
//                    productcategoryRepository.deleteByProductAndCategory(id, category_id);
//                }
//            }
//            return mapper.ToResponseDTO(repository.findById(category_id).get());
//        } else {
//            throw new EntityNotFoundException();
//        }
//    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void CacheId() {
        for (Long l : repository.getAllId()) {
            Util.addCategory(l);
        }
    }
}
