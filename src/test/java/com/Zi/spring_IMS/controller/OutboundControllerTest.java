package com.Zi.spring_IMS.controller;

import com.Zi.spring_IMS.model.dto.OutboundDTO;
import com.Zi.spring_IMS.model.entity.OutboundTransaction;
import com.Zi.spring_IMS.service.OutboundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//@AutoConfigureMockMvc
//public class OutboundControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private OutboundService outboundService;
//
//    private OutboundDTO outboundDTO;
//    private OutboundTransaction outboundTransaction;
//    private List<OutboundTransaction> outboundTransactionList;
//    private LocalDateTime transactionTime;
//
//    @BeforeEach
//    void setUp() {
//        outboundDTO = OutboundDTO.builder()
//                .product_id(1)
//                .quantity(5)
//                .user_id(101)
//                .notes("Test outbound transaction")
//                .build();
//
//        transactionTime = LocalDateTime.now().withNano((LocalDateTime.now().getNano() / 100) * 100); // Truncate to microseconds
//        outboundTransaction = new OutboundTransaction();
//        outboundTransaction.setId(1);
//        outboundTransaction.setProduct_id(1);
//        outboundTransaction.setQuantity(5);
//        outboundTransaction.setCost_per_unit(1.0);
//        outboundTransaction.setTotal_cost(5.0);
//        outboundTransaction.setUser_id(101);
//        outboundTransaction.setTransaction_time(transactionTime); // truncate to microseconds
//        outboundTransaction.setNotes("Test outbound transaction");
//
//        outboundTransactionList = List.of(outboundTransaction);
//    }
//
//    @Test
//    void testCreateOutboundTransaction() throws Exception {
//        when(outboundService.createOutboundTransaction(outboundDTO)).thenReturn(outboundTransaction);
//
//        mockMvc.perform(post("/outbound_transactions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"product_id\":1, \"quantity\":5, \"user_id\":101, \"notes\": \"Test outbound transaction\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(outboundTransaction.getId()))
//                .andExpect(jsonPath("$.product_id").value(outboundTransaction.getProduct_id()))
//                .andExpect(jsonPath("$.quantity").value(outboundTransaction.getQuantity()))
//                .andExpect(jsonPath("$.cost_per_unit").value(outboundTransaction.getCost_per_unit()))
//                .andExpect(jsonPath("$.total_cost").value(outboundTransaction.getTotal_cost()))
//                //.andExpect(jsonPath("$.transaction_time").value(transactionTime.truncatedTo(ChronoUnit.MICROS).toString()))
//                .andExpect(jsonPath("$.user_id").value(outboundTransaction.getUser_id()))
//                .andExpect(jsonPath("$.notes").value(outboundTransaction.getNotes()));
//    }
//
//    @Test
//    void testGetAllOutboundTransactions() throws Exception {
//        when(outboundService.getAllOutboundTransaction()).thenReturn(outboundTransactionList);
//
//        mockMvc.perform(get("/outbound_transactions"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(outboundTransaction.getId()))
//                .andExpect(jsonPath("$[0].product_id").value(outboundTransaction.getProduct_id()))
//                .andExpect(jsonPath("$[0].quantity").value(outboundTransaction.getQuantity()))
//                .andExpect(jsonPath("$[0].cost_per_unit").value(outboundTransaction.getCost_per_unit()))
//                .andExpect(jsonPath("$[0].total_cost").value(outboundTransaction.getTotal_cost()))
//                //.andExpect(jsonPath("$[0].transaction_time").value(transactionTime.truncatedTo(ChronoUnit.MICROS).toString()))
//                .andExpect(jsonPath("$[0].user_id").value(outboundTransaction.getUser_id()))
//                .andExpect(jsonPath("$[0].notes").value(outboundTransaction.getNotes()));
//    }
//
//    @Test
//    void testGetOutboundTransactionById() throws Exception {
//        when(outboundService.getOutBoundTransactionById(1)).thenReturn(outboundTransaction);
//
//        mockMvc.perform(get("/outbound_transactions/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(outboundTransaction.getId()))
//                .andExpect(jsonPath("$.product_id").value(outboundTransaction.getProduct_id()))
//                .andExpect(jsonPath("$.quantity").value(outboundTransaction.getQuantity()))
//                .andExpect(jsonPath("$.cost_per_unit").value(outboundTransaction.getCost_per_unit()))
//                .andExpect(jsonPath("$.total_cost").value(outboundTransaction.getTotal_cost()))
//                //.andExpect(jsonPath("$.transaction_time").value(transactionTime.truncatedTo(ChronoUnit.MICROS).toString()))
//                .andExpect(jsonPath("$.user_id").value(outboundTransaction.getUser_id()))
//                .andExpect(jsonPath("$.notes").value(outboundTransaction.getNotes()));
//    }
//}
