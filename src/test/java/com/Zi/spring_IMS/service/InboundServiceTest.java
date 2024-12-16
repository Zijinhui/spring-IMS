package com.Zi.spring_IMS.service;
import com.Zi.spring_IMS.model.dto.InboundDTO;
import com.Zi.spring_IMS.model.entity.InboundTransaction;
import com.Zi.spring_IMS.model.entity.Product;
import com.Zi.spring_IMS.model.entity.Product_Status;
import com.Zi.spring_IMS.model.mapper.InboundMapper;
import com.Zi.spring_IMS.model.mapper.ProductMapper;
import com.Zi.spring_IMS.repository.InboundRepository;
import com.Zi.spring_IMS.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//public class InboundServiceTest {
//    @Mock
//    private InboundRepository inboundRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private InboundMapper inboundMapper;
//
//    @Mock
//    private ProductMapper productMapper;
//
//    @InjectMocks
//    private InboundService inboundService;
//
//    private InboundDTO inboundDTO;
//    private InboundTransaction inboundTransaction;
//    private Product product;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        inboundDTO = InboundDTO.builder()
//                .product_id(1)
//                .quantity(10)
//                .unit_of_measurement("kg")
//                .total_cost(100.0)
//                .received_date(LocalDate.now())
//                .supplier_name("Test Supplier")
//                .supplier_contact("1234567890")
//                .batch_number("BATCH001")
//                .user_id(101)
//                .notes("Test Notes")
//                .build();
//
//        inboundTransaction = new InboundTransaction();
//        inboundTransaction.setId(1);
//        inboundTransaction.setProduct_id(1);
//        inboundTransaction.setQuantity(10);
//        inboundTransaction.setUnit_of_measurement("kg");
//        inboundTransaction.setTotal_cost(100.0);
//        inboundTransaction.setCost_per_unit(10.0);
//        inboundTransaction.setReceived_date(LocalDate.now());
//        inboundTransaction.setSupplier_name("Test Supplier");
//        inboundTransaction.setSupplier_contact("1234567890");
//        inboundTransaction.setBatch_number("BATCH001");
//        inboundTransaction.setTransaction_time(LocalDateTime.now());
//        inboundTransaction.setUser_id(101);
//        inboundTransaction.setNotes("Test Notes");
//
//        product = new Product();
//        product.setId(1);
//        product.setName("Test Product");
//        product.setCategory("Seafood");
//        product.setQuantity(10);
//        product.setUnit_of_measurement("kg");
//        product.setTotal_cost(100.0);
//        product.setReorder_level(5);
//        product.setReorder_quantity(10);
//    }
//
//    @Test
//    void createInboundTransaction_whenValidInput_shouldUpdateProduct() {
//        when(inboundMapper.toInboundTransactionEntity(inboundDTO)).thenReturn(inboundTransaction);
//        when(productRepository.findById(1)).thenReturn(Optional.of(product));
//        when(productMapper.updateInboundDtoToProductEntity(inboundDTO)).thenReturn(product);
//        when(inboundRepository.save(any(InboundTransaction.class))).thenReturn(inboundTransaction);
//
//        InboundTransaction result = inboundService.createInboundTransaction(inboundDTO);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(inboundTransaction.getId());
//        assertThat(result.getProduct_id()).isEqualTo(inboundDTO.getProduct_id());
//
//// Assert
//        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
//        verify(productRepository).save(productCaptor.capture());
//
//        Product updatedProduct = productCaptor.getValue();
//        assertThat(updatedProduct.getQuantity()).isEqualTo(20); // 10 (initial) + 10 (inbound quantity)
//        assertThat(updatedProduct.getTotal_cost()).isEqualTo(200.0); // 100.0 (initial) + 100.0 (inbound cost)
////        assertThat(product).isNotNull();
////        assertThat(product.getId()).isEqualTo(product.getId());
////        assertThat(product.getQuantity()).isEqualTo(20); // sum(0+10)
////        assertThat(product.getTotal_cost()).isEqualTo(200.0); // sum(0.0+100.0)
////        assertThat(product.getCost_per_unit()).isEqualTo(10.0); //average(total_cost/total_quantity)
////        assertThat(product.getStatus()).isEqualTo(Product_Status.IN_STOCK);
//
//        // verify Method Calls
//        verify(inboundMapper).toInboundTransactionEntity(inboundDTO);
//        verify(productMapper).updateInboundDtoToProductEntity(inboundDTO);
//        verify(inboundRepository).save(inboundTransaction);
//    }
//
//    @Test
//    void createInboundTransaction_whenProductNotFound() {
//        when(productRepository.findById(2)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> inboundService.createInboundTransaction(inboundDTO))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessage("Product with id: 1 is not found!");
//
//        verifyNoInteractions(inboundMapper);
//        verifyNoInteractions(inboundRepository);
//    }
//
//    @Test
//    void getAllInboundTransactions() {
//        when(inboundRepository.findAll()).thenReturn(List.of(inboundTransaction));
//
//        List<InboundTransaction> result = inboundService.getAllInboundTransactions();
//
//        assertThat(result).isNotNull();
//        assertThat(result).hasSize(1);
//        assertThat(result.get(0)).isEqualTo(inboundTransaction);
//
//        verify(inboundRepository).findAll();
//        // double check no interaction with Product entity
//        verifyNoInteractions(productRepository);
//    }
//
//    @Test
//    void getInboundTransactionById_whenValidId() {
//        when(inboundRepository.findById(1)).thenReturn(Optional.of(inboundTransaction));
//
//        InboundTransaction result = inboundService.getInboundTransactionById(1);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(1);
//
//        verify(inboundRepository).findById(1);
//
//        // double check no interaction with Product entity
//        verifyNoInteractions(productRepository);
//    }
//
//    @Test
//    void getInboundTransactionById_whenInvalidId() {
//        when(inboundRepository.findById(1)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> inboundService.getInboundTransactionById(1))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessage("id is not found!");
//
//        verify(inboundRepository).findById(1);
//
//        // double check no interaction with Product entity
//        verifyNoInteractions(productRepository);
//    }
//}
