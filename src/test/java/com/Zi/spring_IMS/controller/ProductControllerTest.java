package com.Zi.spring_IMS.controller;

import com.Zi.spring_IMS.model.dto.ProductDTO;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.model.entity.Product_Status;
import com.Zi.spring_IMS.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//@AutoConfigureMockMvc
//public class ProductControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProductService productService;
//
//    private ProductDTO productDTO;
//    private Product product;
//    private List<Product> productList;
//
//    @BeforeEach
//    void setUp() {
//        productDTO = ProductDTO.builder()
//                .name("Salmon")
//                .category("Seafood")
//                .reorder_level(5)
//                .reorder_quantity(10)
//                .build();
//
//        product = new Product();
//        product.setId(1);
//        product.setName("Salmon");
//        product.setCategory("Seafood");
//        product.setQuantity(10);
//        product.setReorder_level(5);
//        product.setReorder_quantity(10);
//        product.setStatus(Product_Status.IN_STOCK);
//
//        productList = List.of(product);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"}) // Mock an admin user
//    void testCreateProduct() throws Exception {
//        when(productService.createProduct(productDTO)).thenReturn(product);
//
//        mockMvc.perform(post("/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\": \"Salmon\", \"category\": \"Seafood\", \"reorder_level\":5, \"reorder_quantity\":10}"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(product.getId()))
//                .andExpect(jsonPath("$.name").value(product.getName()))
//                .andExpect(jsonPath("$.category").value(product.getCategory()))
//                .andExpect(jsonPath("$.reorder_level").value(product.getReorder_level()))
//                .andExpect(jsonPath("$.reorder_quantity").value(product.getReorder_quantity()));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"}) // Mock an admin user
//    void testGetAllProducts() throws Exception {
//        when(productService.getAllProducts()).thenReturn(productList);
//
//        mockMvc.perform(get("/products"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(product.getId()))
//                .andExpect(jsonPath("$[0].name").value(product.getName()))
//                .andExpect(jsonPath("$[0].category").value(product.getCategory()))
//                .andExpect(jsonPath("$[0].reorder_level").value(product.getReorder_level()))
//                .andExpect(jsonPath("$[0].reorder_quantity").value(product.getReorder_quantity()));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"}) // Mock an admin user
//    void testGetProductById() throws Exception {
//        when(productService.getProductById(1)).thenReturn(product);
//
//        mockMvc.perform(get("/products/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(product.getId()))
//                .andExpect(jsonPath("$.name").value(product.getName()))
//                .andExpect(jsonPath("$.category").value(product.getCategory()))
//                .andExpect(jsonPath("$.reorder_level").value(product.getReorder_level()))
//                .andExpect(jsonPath("$.reorder_quantity").value(product.getReorder_quantity()));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"}) // mock an admin user
//    void testDeleteProductById() throws Exception {
//        when(productService.deleteProductById(1)).thenReturn(true);
//
//        mockMvc.perform(delete("/products/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("true"));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"}) // mock an admin user
//    void testUpdateProductById() throws Exception {
//        // create a new ProductDTO with updated values
//        ProductDTO updatedProductDTO = ProductDTO.builder()
//                .name("Coke")
//                .category("Beverage")
//                .reorder_level(10)
//                .reorder_quantity(20)
//                .build();
//
//        when(productService.updateProductById(1, updatedProductDTO)).thenReturn(product);
//
//        String productJson = new ObjectMapper().writeValueAsString(updatedProductDTO);
//
//        mockMvc.perform(put("/products/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(productJson)) // pass the serialized JSON payload
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(product.getId()))
//                .andExpect(jsonPath("$.name").value(product.getName()))
//                .andExpect(jsonPath("$.category").value(product.getCategory()))
//                .andExpect(jsonPath("$.reorder_level").value(product.getReorder_level()))
//                .andExpect(jsonPath("$.reorder_quantity").value(product.getReorder_quantity()));
//    }
//}
