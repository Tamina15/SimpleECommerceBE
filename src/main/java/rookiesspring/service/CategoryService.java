/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookiesspring.dto.CategoryDTO;
import rookiesspring.dto.response.CategoryResponseDTO;
import rookiesspring.dto.response.custom.CategoryResponseDTOShort;
import rookiesspring.exception.ResourceNotFoundException;
import rookiesspring.mapper.CategoryMapper;
import rookiesspring.model.Category;
import rookiesspring.model.Product;
import rookiesspring.repository.CategoryRepository;
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
    ProductService productService;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CategoryResponseDTOShort> findAll() {
        return repository.findAllProjectedBy();
    }

    public CategoryResponseDTOShort findById(long id) {
        return repository.findOneProjectedById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    public List<CategoryResponseDTO> findAllFull() {
        return mapper.ToResponseDTOList(repository.findAll());
    }

    public CategoryResponseDTO findByIdFull(long id) {
        return mapper.ToResponseDTO(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException()));
    }

    public List<CategoryResponseDTOShort> findAllByName(String name) {
        return repository.findAllProjectedByNameContainsIgnoreCase(name);
    }

    public CategoryResponseDTO save(CategoryDTO categoryDTO) {
        Category c = mapper.toEntity(categoryDTO);
        c = repository.save(c);
        if (categoryDTO.product_id() != null) {
            addProduct(c.getId(), categoryDTO.product_id());
        }
        return mapper.ToResponseDTO(c);
    }

    public CategoryResponseDTO addProduct(long category_id, long[] product_ids) {
        Category c = repository.findById(category_id).orElseThrow(() -> new ResourceNotFoundException());
        for (long id : product_ids) {
            Product p = productService.repository.getReferenceById(id);
            p.addCategory(c);
            productService.repository.save(p);
        }
        return mapper.ToResponseDTO(c);
    }

//    public CategoryResponseDTO save(CategoryDTO categoryDTO) {
//        Category c = mapper.toEntity(categoryDTO);
//        Category result = repository.save(c);
//        List<Product_Category> list_pc = new ArrayList();
//        for (long id : categoryDTO.product_id()) {
//            Product product = new Product(id);
//            Product_Category pc = new Product_Category(product, result);
//            list_pc.add(pc);
//        }
//        result.setProducts(list_pc);
//        result = repository.save(result);
//
//        return mapper.ToResponseDTO(result);
//    }
//
//    public CategoryResponseDTO addProduct(long category_id, long[] product_ids) {
//        Category category = repository.findId(category_id).orElseThrow(() -> new ResourceNotFoundException());
//        List<Product_Category> list_pc = category.getProducts();
//        for (long id : product_ids) {
//            Product product = new Product(id);
//            Product_Category pc = new Product_Category(product, category);
//            if (!list_pc.contains(pc)) {
//                list_pc.add(pc);
//            }
//        }
//        Category result = repository.save(category);
//        return mapper.ToResponseDTO(result);
//    }
    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}
