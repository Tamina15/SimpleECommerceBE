/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookiesspring.dto.ImageDTO;
import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.dto.response.custom.ProductResponseDTOShort;
import rookiesspring.dto.update.ProductUpdateDTO;
import rookiesspring.exception.ResourceNotFoundException;
import rookiesspring.mapper.ImageMapper;
import rookiesspring.mapper.ProductMapper;
import rookiesspring.model.Category;
import rookiesspring.model.Image;
import rookiesspring.model.Product;
import rookiesspring.model.composite_model.ProductCategory;
import rookiesspring.repository.CategoryRepository;
import rookiesspring.repository.ImageRepository;
import rookiesspring.repository.ProductCategoryRepository;
import rookiesspring.repository.ProductRepository;
import rookiesspring.repository.RateRepository;
import rookiesspring.service.interfaces.ProductServiceInterface;
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

    /**
     * Need to change if find all category is empty
     *
     * @param name
     * @param category_id
     * @param from
     * @param to
     * @return
     */
    public List<ProductResponseDTO> findAll(String name, long[] category_id, LocalDateTime from, LocalDateTime to) {
        if (from == null) {
            from = Util.minDateTime;
        }
        if (to == null) {
            to = LocalDateTime.now();
        }

        if (name == null) {
            name = "";
        }

        if (category_id == null || category_id.length == 0) {
            if ("".equals(name)) {
                return mapper.ToResponseDTOList(repository.findAll(from, to));
            }
            category_id = categoryRepository.getAllId();
        }
        return mapper.ToResponseDTOList(repository.findAll(name, category_id, from, to));
    }

    public ProductResponseDTOShort findOneById(long id) {
        return repository.findProjectedById(id);
    }

    public ProductResponseDTO findOneByIdFull(long id) {
        Product p = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        return mapper.ToResponseDTO(p);
    }

// done
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
        if (product_dto.images() != null) {
            for (ImageDTO i : product_dto.images()) {
                Image image = imageMapper.toEntity(i);
                image.setProduct(p);
//                image = imageRepository.save(image);
                p.addImage(image);
            }
        }
        repository.save(p);
        return mapper.ToResponseDTO(p);
    }

//done
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

    public ProductResponseDTO addImages(long product_id, ImageDTO[] images) {
        Product p = repository.getReferenceById(product_id);
        if (images.length != 0) {
            for (ImageDTO i : images) {
                if (!imageRepository.existsByName(i.name())) {
                    Image image = imageMapper.toEntity(i);
                    image.setProduct(p);
                    p.addImage(image);
                }
            }
            repository.save(p);
        }
        return mapper.ToResponseDTO(p);
    }

    @Transactional(rollbackOn = {RuntimeException.class})
    public ProductResponseDTO removeCategories(long product_id, long[] category_id) {
        if (category_id.length != 0) {
            for (long id : category_id) {
                System.out.println(id);
                productCategoryRepository.deleteByProductIdAndCategoryId(product_id, id);
                
            }
        }
        productCategoryRepository.flush();
        return mapper.ToResponseDTO(repository.findById(product_id).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Transactional(rollbackOn = {RuntimeException.class})
    public ProductResponseDTO removeImages(long product_id, long[] images_id) {
        Product p = repository.findById(product_id).orElseThrow(() -> new EntityNotFoundException());
        if (images_id.length != 0) {
            for (long id : images_id) {
                Image i = imageRepository.getReferenceById(id);
                p.removeImage(i);
                imageRepository.delete(i);
            }
        }
        return mapper.ToResponseDTO(p);
    }

    public ProductResponseDTO update(ProductUpdateDTO product_dto) {
        Product p = repository.getReferenceById(product_dto.id());
        p.setName(product_dto.name());
        p.setDescription(product_dto.description());
        p.setPrice(product_dto.price());
        p.setAmount(product_dto.amount());
        repository.save(p);
        p = repository.findById(p.getId()).orElseThrow(() -> new ResourceNotFoundException());
        return mapper.ToResponseDTO(p);
    }

    public void delete(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }

    @Autowired
    RateRepository rateRepository;

//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void doSomethingAfterStartup() {
//        List<Product> products = repository.findAll();
//        for (Product p : products) {
//            List<Rate> rates = rateRepository.findAllByProductId(p.getId());
//            if (!rates.isEmpty()) {
//                double score = 0;
//                for (Rate r : rates) {
//                    score += r.getScore();
//                }
//                score = score / rates.size();
//                p.setRating(Double.toString(score));
//                repository.save(p);
//            }
//        }
//        System.out.println("Finding all Rating");
//    }
}
