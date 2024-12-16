package com.Zi.spring_IMS.model.mapper;


import com.Zi.spring_IMS.model.dto.OutboundDTO;
import com.Zi.spring_IMS.model.entity.OutboundTransaction;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OutboundMapper {
    private final ProductRepository productRepository;

    @Autowired
    public OutboundMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public OutboundTransaction toOutboundTransactionEntity(OutboundDTO outboundDTO) {
        OutboundTransaction outboundTransaction = new OutboundTransaction();

        outboundTransaction.setProduct_id(outboundDTO.getProduct_id() );
        outboundTransaction.setQuantity(outboundDTO.getQuantity());
        // fetch this Product
        Product existingProduct = productRepository.findById(outboundTransaction.getProduct_id())
                .orElseThrow(()-> new RuntimeException("Product with id: "+ outboundTransaction.getProduct_id() +" is not found!"));
        outboundTransaction.setCost_per_unit(existingProduct.getCost_per_unit());
        // calculate the total cost
        outboundTransaction.setTotal_cost(existingProduct.getCost_per_unit() * outboundDTO.getQuantity());

        outboundTransaction.setTransaction_time(LocalDateTime.now());
        outboundTransaction.setUser_id(outboundDTO.getUser_id());
        outboundTransaction.setNotes(outboundDTO.getNotes());

        return outboundTransaction;
    }

}
