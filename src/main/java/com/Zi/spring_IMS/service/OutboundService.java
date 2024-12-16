package com.Zi.spring_IMS.service;

import com.Zi.spring_IMS.model.dto.OutboundDTO;
import com.Zi.spring_IMS.model.entity.OutboundTransaction;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.model.mapper.OutboundMapper;
import com.Zi.spring_IMS.model.mapper.ProductMapper;
import com.Zi.spring_IMS.repository.OutboundRepository;
import com.Zi.spring_IMS.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OutboundService {
    private final OutboundRepository outboundRepository;
    private final OutboundMapper outboundMapper;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private AlertService alertService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    public OutboundService(OutboundRepository outboundRepository,
                           OutboundMapper outboundMapper,
                           ProductMapper productMapper,
                           ProductRepository productRepository,
                           AlertService alertService) {
        this.outboundRepository = outboundRepository;
        this.outboundMapper = outboundMapper;
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.alertService = alertService;
    }

    public OutboundTransaction createOutboundTransaction(OutboundDTO outboundDTO) {
        //convert OutboundDTO to Outbound Entity
        OutboundTransaction outboundTransaction = outboundMapper.toOutboundTransactionEntity(outboundDTO);

        //update the product associate with the outbound transaction
        Product existingProduct = productMapper.updateOutboundDtoToProductEntity(outboundDTO);
        productRepository.save(existingProduct);

        // Trigger reorder alert if necessary
        CompletableFuture.runAsync(() -> {
            try {
                alertService.checkAndSendReorderAlert(existingProduct);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, executorService);


        return outboundRepository.save(outboundTransaction);
    }

    public List<OutboundTransaction> getAllOutboundTransaction() { return outboundRepository.findAll(); }

    public OutboundTransaction getOutBoundTransactionById(Integer id) { return outboundRepository.findById(id).orElseThrow(()->new RuntimeException("id is not found!")); }

}
