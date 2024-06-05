/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.doNothing;

import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.ProductRequestDTO;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.dto.response.custom.CategoryResponseDTOShort;

import rookiesspring.mapper.ProductMapper;

import rookiesspring.model.Image;
import rookiesspring.model.Product;

import rookiesspring.repository.CategoryRepository;
import rookiesspring.repository.ProductCategoryRepository;
import rookiesspring.repository.ProductRepository;

import rookiesspring.util.Util;

import static org.mockito.Mockito.when;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import rookiesspring.dto.update.ProductUpdateDTO;
import rookiesspring.model.Category;
import rookiesspring.model.composite_model.ProductCategory;

/**
 *
 * @author HP
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    Product product;
    ProductDTO dto;
    ProductUpdateDTO updateDTO;

    public ProductServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setName("Name 1");
        product.setDescription("Description 1");
        product.setPrice(1);
        product.setAmount(1);
        product.setRating(1);
        product.setTotal_count_rating(1);
        product.setCategory(new HashSet<>());
        product.setImages(new HashSet<>());

        dto = new ProductDTO("Name 1", "Description 1", 1d, 1, new long[1]);
        updateDTO = new ProductUpdateDTO(1, "Name 1", "Description 1", 2, 2);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testFindAll_shouldReturnListOfProducts() {
        List<Long> product_id = List.of(1l, 2l);
        long[] category_id = {};
        List<Product> products = List.of(product, new Product(2));
        var dto1 = new ProductResponseDTO(1, "Name 1", "Description 1", 1, 1, "1", false, new HashSet<CategoryResponseDTOShort>(), new HashSet<Image>(), Util.minDateTime, LocalDateTime.now(), false);
        var dto2 = new ProductResponseDTO(2, "Name 2", "Description 2", 2, 2, "2", false, new HashSet<CategoryResponseDTOShort>(), new HashSet<Image>(), Util.minDateTime, LocalDateTime.now(), false);
        ProductRequestDTO request = new ProductRequestDTO();
        when(repository.findAllProductId(Mockito.any(String.class), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.any(), Mockito.any(PageRequest.class))).thenReturn(product_id);
//        when(repository.findAllFeaturedProductId(Mockito.any(String.class), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.any(), Mockito.any(PageRequest.class))).thenReturn(product_id);
        when(repository.findAllWithCategoryAndImage(product_id, category_id)).thenReturn(products);
        when(mapper.ToResponseDTOList(Mockito.anyList())).thenReturn(List.of(dto1, dto2));
        List<ProductResponseDTO> response = service.findAll(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void testCountAll_shouldReturnGreaterOrEqualThanZeroResult() {
        boolean feature = false;
        List<Long> category_id = List.of(1l);
        when(repository.count(Mockito.any(Specification.class))).thenReturn(1l);
        long count = service.countAll(feature, category_id);
        assertThat(count).isGreaterThan(-1);
    }

    @Test
    public void testFindOneById_shouldReturnProductResponseDTO() {
        Long id = 2l;
        product.setId(id);
        var dto = new ProductResponseDTO(id, "Name 1", "Description 1", 1, 1, "1", false, new HashSet<CategoryResponseDTOShort>(), new HashSet<Image>(), Util.minDateTime, LocalDateTime.now(), false);
        when(repository.findById(id)).thenReturn(Optional.of(product));
        when(mapper.ToResponseDTO(Mockito.any(Product.class))).thenReturn(dto);
        ProductResponseDTO response = service.findOneById(id);
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(product.getId());
        assertThat(response.name()).isEqualTo(product.getName());
        assertThat(response.description()).isEqualTo(product.getDescription());
    }

    @Test
    public void testSave_shouldReturnProduct() {
        var response = new ProductResponseDTO(1, "Name 1", "Description 1", 1, 1, "1", false, new HashSet<CategoryResponseDTOShort>(), new HashSet<Image>(), Util.minDateTime, LocalDateTime.now(), false);
        when(mapper.toEntity(dto)).thenReturn(product);
        when(repository.save(product)).thenReturn(product);
        when(mapper.ToResponseDTO(product)).thenReturn(response);
        ProductResponseDTO result = service.save(dto);
        assertThat(result).isNotNull();
    }

    @Test
    public void testUpdate_shouldReturnProduct() {
        var dto1 = new ProductResponseDTO(1, "Name 1", "Description 1", 1, 1, "1", false, new HashSet<CategoryResponseDTOShort>(), new HashSet<Image>(), Util.minDateTime, LocalDateTime.now(), false);
        when(repository.getReferenceById(Mockito.any(Long.class))).thenReturn(product);
        when(repository.save(Mockito.any(Product.class))).thenReturn(product);
        when(mapper.ToResponseDTO(Mockito.any(Product.class))).thenReturn(dto1);
        ProductResponseDTO response = service.update(updateDTO);
        assertThat(response).isNotNull();
    }

    @Test
    public void testUpdateCategories_shouldReturnListOfProducts() {
        Category category = new Category();
        category.setId(1l);
        ProductCategory productCategory = new ProductCategory(product, category);
        var dto1 = new ProductResponseDTO(1, "Name 1", "Description 1", 1, 1, "1", false, new HashSet<CategoryResponseDTOShort>(), new HashSet<Image>(), Util.minDateTime, LocalDateTime.now(), false);
        long[] category_id = {1, 2, 3};
        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(product));
//        when(repository.existsById(Mockito.any(Long.class))).thenReturn(true);
        doNothing().when(productCategoryRepository).deleteByProductId(Mockito.any(Long.class));
        when(categoryRepository.existsById(Mockito.any(Long.class))).thenReturn(true);
        when(categoryRepository.getReferenceById(Mockito.any(Long.class))).thenReturn(category);
        when(productCategoryRepository.save(Mockito.any(ProductCategory.class))).thenReturn(productCategory);
        when(mapper.ToResponseDTO(Mockito.any(Product.class))).thenReturn(dto1);
        assertThat(service.updateCategories(1, category_id)).isNotNull();
    }

    @Test
    public void testDelete_shouldReturnNothing() {
        when(repository.existsById(Mockito.any(Long.class))).thenReturn(true);
//        doNothing().when(repository).delete(product);
        Assertions.assertAll(() -> {
            service.delete(Mockito.any(Long.class), true);
        });
    }

    @Test
    public void testDelete_shouldThrowEntityNotFoundException() {
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.delete(1000, true);
        });
        Assertions.assertTrue("No Product With this Id".contains(exception.getMessage()));
    }

    @Test
    public void testRestore_shouldReturnNothing() {
        when(repository.existsById(Mockito.any(Long.class))).thenReturn(true);
//        doNothing().when(repository).delete(product);
        Assertions.assertAll(() -> {
            service.restore(Mockito.any(Long.class));
        });
    }

    @Test
    public void testRestore_shouldThrowEntityNotFoundException() {
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.restore(1000);
        });
        Assertions.assertTrue("No Product With this Id".contains(exception.getMessage()));

    }

}
