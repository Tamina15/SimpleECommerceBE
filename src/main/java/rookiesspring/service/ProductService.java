/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.ProductRequestDTO;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.dto.response.custom.ProductPagination;
import rookiesspring.dto.update.ProductUpdateDTO;
import rookiesspring.exception.ResourceNotFoundException;
import rookiesspring.mapper.ProductMapper;
import rookiesspring.model.Category;
import rookiesspring.model.Product;
import rookiesspring.model.composite_model.ProductCategory;
import rookiesspring.repository.CategoryRepository;
import rookiesspring.repository.ProductCategoryRepository;
import rookiesspring.repository.ProductRepository;
import rookiesspring.service.interfaces.ProductServiceInterface;
import static rookiesspring.specification.ProductSpecification.*;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class ProductService implements ProductServiceInterface {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductRepository repository, ProductMapper mapper, CategoryRepository categoryRepository, ProductCategoryRepository productCategoryRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Transactional(readOnly = true)
    public ProductPagination findAll(ProductRequestDTO dto) {
        PageRequest page_request = PageRequest.of(dto.getPage(), dto.getLimit(), Sort.by(Sort.Direction.fromString(dto.getOrderBy()), dto.getSortBy()));
        Page<Product> products = repository.findAll(filterSpecsJoinImages(Util.toLongList(dto.getCategory_id()), dto.getName(), dto.getFeatured(), dto.getDeleted(), dto.getFrom(), dto.getTo()), page_request);
        List<ProductResponseDTO> products_list = mapper.ToResponseDTOList(products.toList());
        long total = products.getTotalElements();
        return new ProductPagination(products_list, total);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAllByIdIn(long[] product_id) {
        List<Long> list = new ArrayList();
        for (long l : product_id) {
            list.add(l);
        }
        List<Product> products = repository.findAllByProductIdIn(list);
        return mapper.ToResponseDTOList(products);
    }

    public ProductResponseDTO findOneById(long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        return mapper.ToResponseDTO(product);
    }

    public ProductResponseDTO save(ProductDTO product_dto) {
        Product product = mapper.toEntity(product_dto);
        product = repository.save(product);
        return mapper.ToResponseDTO(product);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public ProductResponseDTO updateCategories(long product_id, long[] category_id) {
        Product product = repository.findById(product_id).orElseThrow(() -> new EntityNotFoundException());
        Set<ProductCategory> set = new HashSet<>();
        productCategoryRepository.deleteByProductId(product_id);
        for (long id : category_id) {
            if (categoryRepository.existsById(id)) {
                Category category = categoryRepository.getReferenceById(id);
                ProductCategory productCategory = new ProductCategory(product, category);
                productCategoryRepository.save(productCategory);
            }
        }
        return mapper.ToResponseDTO(product);
    }

    @Deprecated
    public ProductResponseDTO addCategories(long product_id, long[] category_id) {
        Product product = repository.getReferenceById(product_id);
        if (repository.existsById(product_id)) {
            for (long id : category_id) {
                if (categoryRepository.existsById(id) && !productCategoryRepository.existsByProductIdAndCategoryId(product_id, id)) {
                    Category category = categoryRepository.getReferenceById(id);
                    ProductCategory productCategory = new ProductCategory(product, category);
                    productCategoryRepository.save(productCategory);
                }
            }
        } else {
            throw new EntityNotFoundException();
        }
        return mapper.ToResponseDTO(product);
    }

    @Deprecated()
    @Transactional(rollbackFor = {RuntimeException.class})
    public ProductResponseDTO removeCategories(long product_id, long[] category_id) {
        if (category_id.length != 0) {
            for (long id : category_id) {
                productCategoryRepository.deleteByProductIdAndCategoryId(product_id, id);
            }
        }
        productCategoryRepository.flush();
        return mapper.ToResponseDTO(repository.findById(product_id).orElseThrow(() -> new EntityNotFoundException()));
    }

    public ProductResponseDTO update(ProductUpdateDTO product_dto) {
        Product p = repository.getReferenceById(product_dto.id());
        p.setName(product_dto.name());
        p.setDescription(product_dto.description());
        p.setPrice(product_dto.price());
        p.setAmount(product_dto.amount());
        p.setFeature(product_dto.feature());
        repository.save(p);
        return mapper.ToResponseDTO(p);
    }

    public void delete(long id, boolean forcedDelete) {
        if (repository.existsById(id)) {
            if (forcedDelete) {

            } else {
                repository.deleteById(id);
            }
        } else {
            throw new EntityNotFoundException("No Product With this Id");
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void restore(long id) {
        if (repository.existsById(id)) {
            repository.restoreProduct(id);
        } else {
            throw new EntityNotFoundException("No Product With this Id");
        }
    }

}
