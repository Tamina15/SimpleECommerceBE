/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.dto.response.custom.ProductResponseDTOShort;
import rookiesspring.mapper.ProductMapper;
import rookiesspring.model.Category;
import rookiesspring.model.Product;
import rookiesspring.model.Rate;
import rookiesspring.repository.CategoryRepository;
import rookiesspring.repository.ProductRepository;
import rookiesspring.repository.RateRepository;
import rookiesspring.service.interfaces.ProductServiceInterface;

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

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProductResponseDTOShort> findAll() {
        return repository.findAllProjectedBy();
    }

    public List<ProductResponseDTO> findAllFull() {
        return mapper.ToResponseDTOList(repository.findAll());
    }

    public ProductResponseDTOShort findOneById(long id) {
        return repository.findProjectedById(id);
    }

    public ProductResponseDTO findOneByIdFull(long id) {
        Product p = repository.findById(id).orElse(new Product());
        return mapper.ToResponseDTO(p);
    }

    public List<ProductResponseDTOShort> findAllByCategory(long[] category_id) {
        return repository.findAllProjectedByCategoryIn(category_id);
    }

    public ProductResponseDTO save(ProductDTO product_dto) {
        Product p = mapper.toEntity(product_dto);

        if (product_dto.category_id() != null) {
            Set<Category> set = new HashSet();
            for (long id : product_dto.category_id()) {
                Category c = categoryRepository.getReferenceById(id);
                set.add(c);
            }
            p.setCategory(set);
        }
        p = repository.save(p);
        return mapper.ToResponseDTO(p);
    }

    public boolean addCategory(long product_id, long category_id) {
        Product p = repository.getReferenceById(product_id);
        Category c = categoryRepository.getReferenceById(category_id);
        boolean success = p.addCategory(c);
        repository.save(p);
        return success;
    }

    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }
    @Autowired
    RateRepository rateRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void doSomethingAfterStartup() {
        List<Product> products = repository.findAll();
        for (Product p : products) {
            List<Rate> rates = rateRepository.findAllByProductId(p.getId());
            if (!rates.isEmpty()) {
                double score = 0;
                for (Rate r : rates) {
                    score += r.getScore();
                    System.out.println(p.getId() + " " + r.getScore());
                }
                score = score / rates.size();
                p.setRating(Double.toString(score));
                repository.save(p);
            }
        }
        System.out.println("Finding all Rating");
    }

}
