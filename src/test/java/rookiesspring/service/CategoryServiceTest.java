/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
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
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 *
 * @author HP
 */
@ExtendWith(MockitoExtension.class)

public class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    CategoryRepository repository;

    @Mock
    CategoryMapper mapper;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductCategoryRepository productcategoryRepository;

    Category category;
    CategoryResponseDTOShort responseShort;
    CategoryDTO dto;
    CategoryResponseDTO responseDTO;
    ProductCategory productCategory;
    Product product;
    CategoryUpdateDTO updateDTO;
    Category savedCategory;

    public CategoryServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setName("Category 1");
        category.setDescription("Description 1");
        category.setProducts(Set.of());
        responseShort = new CategoryResponseDTOShort(1, "Category 1", "Description 1");
        long[] product_id = {1};
        dto = new CategoryDTO("Category 1", "Description 1", product_id);
        responseDTO = new CategoryResponseDTO(1, "Category 1", "Description 1", List.of(), false);
        product = new Product(1);
        productCategory = new ProductCategory(product, category);
        updateDTO = new CategoryUpdateDTO(1, "Category 1", "Description 1");
        savedCategory = new Category();
        savedCategory.setId(1l);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testFindAll_shouldReturnList() {
        when(repository.findAllProjectedByNameContainsIgnoreCase(Mockito.anyString())).thenReturn(List.of(responseShort));
        List<CategoryResponseDTOShort> result = service.findAll("");
        assertThat(result).isNotEmpty();
    }

    @Test
    public void testFindById_shouldReturnCategory() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        when(mapper.ToResponseDTO(Mockito.any(Category.class))).thenReturn(responseDTO);
        CategoryResponseDTO result = service.findById(1);
        assertThat(result.id()).isEqualTo(responseDTO.id());
    }

    @Test
    public void testFindById_shouldThrowException() {
        when(repository.findById(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.findById(1);
        });
        assertThat(exception.getMessage()).isNull();
    }

    @Test
    public void testSave_shouldReturnCategoryResponseDTO() {
        when(mapper.toEntity(dto)).thenReturn(savedCategory);
        when(repository.save(Mockito.any(Category.class))).thenReturn(savedCategory);
        when(productRepository.getReferenceById(Mockito.anyLong())).thenReturn(new Product(1));
        when(productcategoryRepository.save(Mockito.any(ProductCategory.class))).thenReturn(productCategory);
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        when(mapper.ToResponseDTO(Mockito.any(Category.class))).thenReturn(responseDTO);

        CategoryResponseDTO result = service.save(dto);

        assertThat(result.id()).isEqualTo(1);
    }

    @Test
    public void testUpdate_shouldReturnCategoryResponseDTO() {
        when(repository.getReferenceById(Mockito.anyLong())).thenReturn(savedCategory);
        when(repository.save(Mockito.any(Category.class))).thenAnswer((invocation) -> {
            var c = (Category) invocation.getArgument(0);
            assertThat(c.getName()).isEqualTo(updateDTO.name());
            assertThat(c.getDescription()).isEqualTo(updateDTO.description());
            return savedCategory;
        });
        when(mapper.ToResponseDTOShort(Mockito.any(Category.class))).thenReturn(responseShort);
        CategoryResponseDTOShort result = service.update(updateDTO);
        assertThat(result).isNotNull();
    }

    @Test
    public void testDelete() {
        when(repository.existsById(Mockito.anyLong())).thenReturn(true);
        doNothing().when(repository).deleteById(Mockito.anyLong());
        Assertions.assertAll(() -> {
            service.delete(1);
        });
    }

}
