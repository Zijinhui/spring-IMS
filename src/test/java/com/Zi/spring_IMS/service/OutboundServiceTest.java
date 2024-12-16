package com.Zi.spring_IMS.service;

import com.Zi.spring_IMS.model.dto.OutboundDTO;
import com.Zi.spring_IMS.model.entity.OutboundTransaction;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.model.entity.Product_Status;
import com.Zi.spring_IMS.model.mapper.OutboundMapper;
import com.Zi.spring_IMS.model.mapper.ProductMapper;
import com.Zi.spring_IMS.repository.OutboundRepository;
import com.Zi.spring_IMS.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


//public class OutboundServiceTest {
//    @Mock
//    private OutboundRepository outboundRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private ProductMapper productMapper;
//
//    @Mock
//    private OutboundMapper outboundMapper;
//
//    @Mock
//    private AlertService alertService;
//
//    @Mock
//    private ExecutorService executorService;
//
//    @InjectMocks
//    private OutboundService outboundService;
//
//    private OutboundDTO outboundDTO;
//    private OutboundTransaction outboundTransaction;
//    private Product existingProduct;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        outboundDTO = OutboundDTO.builder()
//                .product_id(1)
//                .quantity(5)
//                .user_id(101)
//                .notes("Test outbound transaction")
//                .build();
//
//        outboundTransaction = new OutboundTransaction();
//        outboundTransaction.setId(1);
//        outboundTransaction.setProduct_id(1);
//        outboundTransaction.setQuantity(5);
//        outboundTransaction.setCost_per_unit(10.0);
//        outboundTransaction.setTotal_cost(50.0);
//        outboundTransaction.setTransaction_time(LocalDateTime.now());
//        outboundTransaction.setUser_id(101);
//        outboundTransaction.setNotes("Test outbound transaction");
//
//        existingProduct = new Product();
//        existingProduct.setId(1);
//        existingProduct.setName("Salmon");
//        existingProduct.setCategory("Seafood");
//        existingProduct.setQuantity(10);
//        existingProduct.setCost_per_unit(10.0);
//        existingProduct.setTotal_cost(100.0);
//        existingProduct.setReorder_level(5);
//        existingProduct.setReorder_quantity(10);
//        existingProduct.setStatus(Product_Status.IN_STOCK);
//    }
//
//    @Test
//    void createOutboundTransaction_whenValidInput() {
////       OutboundDTO outboundDTO = OutboundDTO.builder()
////                .product_id(1)
////                .quantity(5)
////                .user_id(101)
////                .notes("Test outbound transaction")
////                .build();
////
////        OutboundTransaction outboundTransaction = new OutboundTransaction();
////        outboundTransaction.setId(1);
////        outboundTransaction.setProduct_id(1);
////        outboundTransaction.setQuantity(5);
////        outboundTransaction.setCost_per_unit(10.0);
////        outboundTransaction.setTotal_cost(50.0);
////        outboundTransaction.setTransaction_time(LocalDateTime.now());
////        outboundTransaction.setUser_id(101);
////        outboundTransaction.setNotes("Test outbound transaction");
////
////        Product existingProduct = new Product();
////        existingProduct.setId(1);
////        existingProduct.setName("Salmon");
////        existingProduct.setCategory("Seafood");
////        existingProduct.setQuantity(10);
////        existingProduct.setCost_per_unit(10.0);
////        existingProduct.setTotal_cost(100.0);
////        existingProduct.setReorder_level(5);
////        existingProduct.setReorder_quantity(10);
////        existingProduct.setStatus(Product_Status.IN_STOCK);
//
//        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
//        when(productMapper.updateOutboundDtoToProductEntity(outboundDTO)).thenReturn(existingProduct);
//        when(outboundMapper.toOutboundTransactionEntity(outboundDTO)).thenReturn(outboundTransaction);
//        when(outboundRepository.save(any(OutboundTransaction.class))).thenReturn(outboundTransaction);
//
//        OutboundTransaction result = outboundService.createOutboundTransaction(outboundDTO);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(outboundTransaction.getId());
//        assertThat(result.getProduct_id()).isEqualTo(outboundTransaction.getProduct_id());
//        assertThat(result.getQuantity()).isEqualTo(outboundTransaction.getQuantity());
//        assertThat(result.getTotal_cost()).isEqualTo(outboundTransaction.getTotal_cost());
//
//        verify(productRepository).findById(1);
//        verify(productMapper).updateOutboundDtoToProductEntity(outboundDTO);
//        verify(productRepository).save(existingProduct);
//        verify(alertService).checkAndSendReorderAlert(existingProduct);
//        verify(outboundRepository).save(outboundTransaction);
//    }
//
////    @Test
////    void createOutboundTransaction_whenProductNotFound() {
////        when(productRepository.findById(10)).thenReturn(Optional.empty());
////
////        assertThatThrownBy(() -> outboundService.createOutboundTransaction(outboundDTO))
////                .isInstanceOf(RuntimeException.class)
////                .hasMessage("Product not found with id: 1");
////
////        verify(productRepository).findById(1);
////        verifyNoInteractions(outboundMapper);
////        verifyNoInteractions(outboundRepository);
////    }
//
////    @Test
////    void createOutboundTransaction_whenInsufficientStock() {
////        existingProduct.setQuantity(3); // Less than the outbound quantity
////        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
////        when(outboundService.createOutboundTransaction(outboundDTO)).thenReturn(Optional.of(outboundTransaction));
////
////        assertThatThrownBy(() -> outboundService.createOutboundTransaction(outboundDTO))
////                .isInstanceOf(IllegalArgumentException.class)
////                .hasMessage("Insufficient stock. Available quantity: 3");
////
////        verify(productRepository).findById(1);
////        verifyNoInteractions(outboundMapper);
////        verifyNoInteractions(outboundRepository);
////    }
//
//    @Test
//    void getAllOutboundTransactions() {
//        when(outboundRepository.findAll()).thenReturn(List.of(outboundTransaction));
//
//        List<OutboundTransaction> result = outboundService.getAllOutboundTransaction();
//
//        assertThat(result).hasSize(1);
//        assertThat(result).contains(outboundTransaction);
//
//        verify(outboundRepository).findAll();
//    }
//
//    @Test
//    void getOutboundTransactionById_whenValidId() {
//        when(outboundRepository.findById(1)).thenReturn(Optional.of(outboundTransaction));
//
//        OutboundTransaction result = outboundService.getOutBoundTransactionById(1);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(outboundTransaction.getId());
//
//        verify(outboundRepository).findById(1);
//    }
//
//    @Test
//    void getOutboundTransactionById_whenInvalidId() {
//        when(outboundRepository.findById(1)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> outboundService.getOutBoundTransactionById(1))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessage("id is not found!");
//
//        verify(outboundRepository).findById(1);
//    }
//}
