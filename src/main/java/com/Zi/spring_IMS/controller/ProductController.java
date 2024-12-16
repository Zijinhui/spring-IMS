package com.Zi.spring_IMS.controller;

import com.Zi.spring_IMS.model.dto.ProductDTO;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) { this.productService = productService; }

    @PostMapping
    // used for manager to Product Register (set up: id + name + category + reorder_level + reorder_quantity + status)
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) {return ResponseEntity.ok(productService.createProduct(productDTO)); }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() { return ResponseEntity.ok(productService.getAllProducts()); }

    @GetMapping("/{id}")
    public  ResponseEntity<Product> getProductById(@PathVariable Integer id) { return ResponseEntity.ok(productService.getProductById(id)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable Integer id) { return ResponseEntity.ok(productService.deleteProductById(id)); }


    @PutMapping("/{id}") // allow to update only product name + category + reorder_level + reorder_quantity
    public ResponseEntity<Product> updateProductById(@PathVariable Integer id, @Valid @RequestBody ProductDTO productDTO) { return ResponseEntity.ok(productService.updateProductById(id, productDTO)); }
}
