/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rookiesspring.dto.ImageDTO;
import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.ProductRequestDTO;
import rookiesspring.dto.RateDTO;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.dto.response.RateResponseDTO;
import rookiesspring.dto.update.ProductUpdateDTO;
import rookiesspring.exception.ResourceNotFoundException;
import rookiesspring.mapper.ImageMapper;
import rookiesspring.mapper.ProductMapper;
import rookiesspring.model.Category;
import rookiesspring.model.Image;
import rookiesspring.model.Product;
import rookiesspring.model.Rate;
import rookiesspring.model.User;
import rookiesspring.model.composite_model.ProductCategory;
import rookiesspring.repository.CategoryRepository;
import rookiesspring.repository.ImageRepository;
import rookiesspring.repository.ProductCategoryRepository;
import rookiesspring.repository.ProductRepository;
import rookiesspring.repository.RateRepository;
import rookiesspring.service.interfaces.ProductServiceInterface;
import rookiesspring.specification.ProductSpecification;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class ProductService implements ProductServiceInterface {

    ProductRepository repository;
    ProductMapper mapper;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll(ProductRequestDTO dto) {
        PageRequest page_request = PageRequest.of(dto.getPage(), dto.getLimit(), Sort.by(Sort.Direction.fromString(dto.getOrder()), dto.getSortBy()));
        if (dto.getCategory_id().length == 0) {
            dto.setCategory_id(Util.category_id);
        }
        List<Long> product_id;
        if (dto.isFeatured()) {
            product_id = repository.findAllFeaturedProductId(dto.getName(), dto.getFrom(), dto.getTo(), dto.getCategory_id(), page_request);
        } else {
            product_id = repository.findAllProductId(dto.getName(), dto.getFrom(), dto.getTo(), dto.getCategory_id(), page_request);
        }
        List<Product> products = repository.findAllWithCategoryAndImage(product_id, dto.getCategory_id());
        return mapper.ToResponseDTOList(products);
    }

    public long countAll(boolean feature, List<Long> category_id) {
        if (feature) {
            return repository.count(ProductSpecification.countAllWithCategoryIn(category_id).and(ProductSpecification.countAllFeatured(feature)));
        }
        return repository.count(ProductSpecification.countAllWithCategoryIn(category_id));
    }


    public ProductResponseDTO findOneById(long id) {
        Product p = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        return mapper.ToResponseDTO(p);
    }

    public ProductResponseDTO save(ProductDTO product_dto) {
        Product p = mapper.toEntity(product_dto);
        p = repository.save(p);
        if (product_dto.category_id() != null) {
            for (long id : product_dto.category_id()) {
                Category c = categoryRepository.getReferenceById(id);
                ProductCategory pc = new ProductCategory(p, c);
                productCategoryRepository.save(pc);
                p.addCategory(pc);
            }
        }
        repository.save(p);
        return mapper.ToResponseDTO(p);
    }

    public ProductResponseDTO addCategories(long product_id, long[] category_id) {
        Product product = repository.getReferenceById(product_id);
        if (checkExist(product_id)) {
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

    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }

}
