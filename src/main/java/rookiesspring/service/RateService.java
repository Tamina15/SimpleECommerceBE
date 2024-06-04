/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rookiesspring.dto.RateDTO;
import rookiesspring.dto.response.RateResponseDTO;
import rookiesspring.mapper.RateMapper;
import rookiesspring.model.Product;
import rookiesspring.model.Rate;
import rookiesspring.model.User;
import rookiesspring.repository.ProductRepository;
import rookiesspring.repository.RateRepository;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class RateService {

    private RateRepository repository;
    private RateMapper mapper;
    private ProductRepository productRepository;

    public RateService(RateRepository repository, RateMapper mapper, ProductRepository productRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.productRepository = productRepository;
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public double rateProduct(RateDTO rate_dto, User user) {
        Product product = productRepository.findById(rate_dto.product_id()).orElseThrow(() -> new EntityNotFoundException());
        if (repository.existsByProductIdAndUserId(product.getId(), user.getId())) {
            throw new EntityExistsException("You have already rate this product");
        }
        double current_rating = product.getRating();
        long total = product.getTotal_count_rating();
        Rate rate = new Rate(user, product, rate_dto.rating(), rate_dto.comment());
        repository.save(rate);
        double rating = (current_rating * total + rate_dto.rating()) / (total + 1);
        rating = Double.parseDouble(String.format("%.2f", rating));
        product.setRating(rating);
        product.setTotal_count_rating(total + 1);
        productRepository.save(product);
        return rating;
    }

    public List<RateResponseDTO> getAllRating(Long product_id) {
        if (repository.existsById(product_id)) {
            List<Rate> rates = repository.findAllByProductId(product_id);
            return mapper.ToResponseDTOList(rates);
        } else {
            throw new EntityNotFoundException();
        }
    }

    //    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void CalulateRating() {
//        List<Product> products = repository.findAll();
//        for (Product p : products) {
//            List<Rate> rates = rateRepository.findAllByProductId(p.getId());
//            long count = rateRepository.count(RateSpecification.countAllRating(p.getId()));
//            if (!rates.isEmpty()) {
//                double score = 0;
//                for (Rate r : rates) {
//                    score += r.getScore();
//                }
//                score = score / rates.size();
//                p.setRating(Double.parseDouble(String.format("%.2f", score)));
//                p.setTotal_count_rating(count);
//                repository.save(p);
//            }
//        }
//        System.out.println("Finding all Rating");
//    }
}
