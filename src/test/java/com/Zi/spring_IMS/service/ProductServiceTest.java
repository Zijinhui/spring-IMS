package com.Zi.spring_IMS.service;

import com.Zi.spring_IMS.model.dto.ProductDTO;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.model.entity.Product_Status;
import com.Zi.spring_IMS.model.mapper.ProductMapper;
import com.Zi.spring_IMS.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductDTO productDTO = ProductDTO.builder()
                .name("Test Product")
                .category("Food")
                .reorder_level(10)
                .reorder_quantity(5)
                .build();

        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setCategory("Food");
        product.setReorder_level(10);
        product.setReorder_quantity(5);
        product.setStatus(Product_Status.IN_STOCK);

        when(productMapper.toProductEntity(productDTO)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(productDTO);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getId()).isEqualTo(1);
        assertThat(createdProduct.getName()).isEqualTo("Test Product");

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Product 2");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertThat(products).hasSize(2);
        assertThat(products).contains(product1, product2);

        verify(productRepository).findAll();
    }

    @Test
    void testGetProductById_whenProductExists() {
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1);

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(1);
        assertThat(foundProduct.getName()).isEqualTo("Test Product");

        verify(productRepository).findById(1);
    }

    @Test
    void testGetProductById_whenProductDoesNotExist() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("id is not found");

        verify(productRepository).findById(1);
    }

    @Test
    void testUpdateProductById() {
        ProductDTO productDTO = ProductDTO.builder()
                .name("Updated Product")
                .category("Updated Category")
                .reorder_level(20)
                .reorder_quantity(10)
                .build();

        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setName("Old Product");
        existingProduct.setCategory("Old Category");
        existingProduct.setReorder_level(5);
        existingProduct.setReorder_quantity(2);

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Updated Product");
        updatedProduct.setCategory("Updated Category");
        updatedProduct.setReorder_level(20);
        updatedProduct.setReorder_quantity(10);

        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(productMapper.toProductEntity(productDTO)).thenReturn(updatedProduct);
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        Product result = productService.updateProductById(1, productDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Product");
        assertThat(result.getCategory()).isEqualTo("Updated Category");

        verify(productRepository).findById(1);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void testDeleteProductById_whenProductExists() {
        when(productRepository.existsById(1)).thenReturn(true);

        Boolean result = productService.deleteProductById(1);

        assertThat(result).isTrue();
        verify(productRepository).deleteById(1);
    }

    @Test
    void testDeleteProductById_whenProductDoesNotExist() {
        when(productRepository.existsById(1)).thenReturn(false);

        assertThatThrownBy(() -> productService.deleteProductById(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("id is not found");

        verify(productRepository).existsById(1);
    }
}
