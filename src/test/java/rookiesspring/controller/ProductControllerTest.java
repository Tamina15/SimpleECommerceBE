/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rookiesspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import rookiesspring.dto.ProductDTO;
import rookiesspring.dto.update.ProductUpdateDTO;
import rookiesspring.model.Product;
import rookiesspring.service.ProductService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rookiesspring.dto.ProductRequestDTO;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import rookiesspring.dto.response.ProductResponseDTO;
import rookiesspring.dto.response.custom.CategoryResponseDTOShort;
import rookiesspring.exception.ResourceNotFoundException;
import rookiesspring.model.Image;
import rookiesspring.util.Util;

/**
 *
 * @author HP
 */
@SpringBootTest
//@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    Product product;
    ProductDTO dto;
    ProductUpdateDTO updateDTO;
    ProductRequestDTO resquestDTO;
    ProductResponseDTO responseDTO;

    public ProductControllerTest() {
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

        dto = new ProductDTO("Name 1", "Description 1", 1d, 1, new long[1], false);
        updateDTO = new ProductUpdateDTO(1, "Name 1", "Description 1", 2, 2, false);
        resquestDTO = new ProductRequestDTO();
        responseDTO = new ProductResponseDTO(1, "Name 1", "Description 1", 1, 1, "1", false, new HashSet<CategoryResponseDTOShort>(), new HashSet<Image>(), Util.minDateTime, LocalDateTime.now(), false);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllProducts method, of class ProductController.
     */
    @Test
    public void testGetAllProducts_shouldReturnListProductResponseDTO() throws Exception {
        List<ProductResponseDTO> list = List.of(responseDTO);
        when(service.findAll(resquestDTO)).thenReturn(list);
        when(service.countAll(Mockito.anyBoolean(), Mockito.anyList())).thenReturn(1l);
        ResultActions response = mockMvc.perform(get("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(resquestDTO)));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test of getProductById method, of class ProductController.
     */
    @Test
    public void testGetProductById_shouldReturnProductResponseDTO() throws Exception {
        when(service.findOneById(Mockito.any(Long.class))).thenReturn(responseDTO);
        ResultActions response = mockMvc.perform(get("/api/v1/products/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetProductById_shouldThrowResourceNotFoundException() throws Exception {
        when(service.findOneById(Mockito.any(Long.class))).thenThrow(ResourceNotFoundException.class);
        ResultActions response = mockMvc.perform(get("/api/v1/products/1"));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
