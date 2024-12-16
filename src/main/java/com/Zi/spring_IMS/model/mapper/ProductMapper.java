package com.Zi.spring_IMS.model.mapper;

import com.Zi.spring_IMS.model.dto.InboundDTO;
import com.Zi.spring_IMS.model.dto.OutboundDTO;
import com.Zi.spring_IMS.model.dto.ProductDTO;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.model.entity.Product_Status;
import com.Zi.spring_IMS.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final ProductRepository productRepository;

    @Autowired
    public ProductMapper(ProductRepository productRepository) { this.productRepository = productRepository; }

    public Product toProductEntity(ProductDTO productDTO) {
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setReorder_level(productDTO.getReorder_level());
        product.setReorder_quantity(productDTO.getReorder_quantity());

        return product;
    }

    public Product updateInboundDtoToProductEntity(InboundDTO inboundDTO) {
        Integer productId = inboundDTO.getProduct_id();
        // fetch the product associated with the inbound transaction
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product with id: "+ productId +" is not found!"));
        //calculate the sum of the quantity
        Integer currentQuantity = existingProduct.getQuantity();
        existingProduct.setQuantity(currentQuantity + inboundDTO.getQuantity());

        existingProduct.setUnit_of_measurement(inboundDTO.getUnit_of_measurement());

        //calculate the sum of the total cost
        Double currentTotalCost = existingProduct.getTotal_cost();
        existingProduct.setTotal_cost(currentTotalCost + inboundDTO.getTotal_cost());
        // calculate average of cost per unit
        Double cost_per_unit = existingProduct.getTotal_cost() / existingProduct.getQuantity();//check if is double
        existingProduct.setCost_per_unit(cost_per_unit);
        existingProduct.setStatus(Product_Status.IN_STOCK);

        return existingProduct;
    }

    public Product updateOutboundDtoToProductEntity(OutboundDTO outboundDTO) {
        // fetch the product associated with the outbound transaction
        Product existingProduct = productRepository.findById(outboundDTO.getProduct_id())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + outboundDTO.getProduct_id()));

        // validate that the outbound quantity is less than or equal to the current quantity
        if (outboundDTO.getQuantity() > existingProduct.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock. Available quantity: " + existingProduct.getQuantity());
        }
        existingProduct.setQuantity(existingProduct.getQuantity() - outboundDTO.getQuantity());
        // deduct total_cost of Product entity
        existingProduct.setTotal_cost(existingProduct.getTotal_cost() - outboundDTO.getQuantity() * existingProduct.getCost_per_unit());

        return existingProduct;
    }
}
