package com.Zi.spring_IMS.service;

import com.Zi.spring_IMS.model.dto.ProductDTO;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.model.mapper.ProductMapper;
import com.Zi.spring_IMS.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Product createProduct(ProductDTO productDTO) { return productRepository.save(productMapper.toProductEntity(productDTO)); }

    public List<Product> getAllProducts() { return productRepository.findAll(); }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("id is not found"));
    }

    public Boolean deleteProductById(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("id is not found");
        }
        productRepository.deleteById(id);
        return true;
    }

    public Product updateProductById(Integer id, ProductDTO productDTO) {
        // fetch existing Product
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        // convert Product DTO to Product Entity
        Product updatedProduct = productMapper.toProductEntity(productDTO);

        // update the fields of the existing product
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setUnit_of_measurement(updatedProduct.getUnit_of_measurement());
        existingProduct.setReorder_level(updatedProduct.getReorder_level());
        existingProduct.setReorder_quantity(updatedProduct.getReorder_quantity());
        existingProduct.setCost_per_unit(updatedProduct.getCost_per_unit());
        existingProduct.setStatus(updatedProduct.getStatus());

        return productRepository.save(existingProduct); }
}
