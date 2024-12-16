package com.Zi.spring_IMS.service;

import com.Zi.spring_IMS.model.dto.InboundDTO;
import com.Zi.spring_IMS.model.entity.InboundTransaction;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.model.mapper.InboundMapper;
import com.Zi.spring_IMS.model.mapper.ProductMapper;
import com.Zi.spring_IMS.repository.InboundRepository;
import com.Zi.spring_IMS.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InboundService {

    private final InboundRepository inboundRepository;
    private final InboundMapper inboundMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public InboundService(InboundRepository inboundRepository, InboundMapper inboundMapper, ProductRepository productRepository, ProductMapper productMapper) {
        this.inboundRepository = inboundRepository;
        this.inboundMapper = inboundMapper;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }
    // for all
    public InboundTransaction createInboundTransaction(InboundDTO inboundDTO) {
        // convert Inbound DTO to InboundTransaction Entity
        InboundTransaction inboundTransaction = inboundMapper.toInboundTransactionEntity(inboundDTO);

        //update the product associate with the inbound transaction
        Product existingProduct = productMapper.updateInboundDtoToProductEntity(inboundDTO);
        productRepository.save(existingProduct);

        return inboundRepository.save(inboundTransaction);
    }

    // for admin. manager
    public List<InboundTransaction> getAllInboundTransactions() { return inboundRepository.findAll(); }

    // for admin. manager
    public InboundTransaction getInboundTransactionById(Integer id) { return inboundRepository.findById(id).orElseThrow(()-> new RuntimeException("id is not found!")); }
}
