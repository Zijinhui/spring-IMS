package com.Zi.spring_IMS.controller;

import com.Zi.spring_IMS.model.dto.InboundDTO;
import com.Zi.spring_IMS.model.entity.InboundTransaction;
import com.Zi.spring_IMS.service.InboundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//@AutoConfigureMockMvc
//public class InboundControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private InboundService inboundService;
//    private InboundDTO inboundDTO;
//    private InboundTransaction inboundTransaction;
//    private List<InboundTransaction> inboundTransactionList;
//    private LocalDateTime transactionTime;
//
//    @BeforeEach
//    void setUp() {
//        inboundDTO = InboundDTO.builder()
//                .product_id(1)
//                .quantity(10)
//                .unit_of_measurement("pond")
//                .total_cost(10.0)
//                .received_date(LocalDate.parse("2024-12-15"))
//                .supplier_name("Test Supplier")
//                .supplier_contact("820-820-8820")
//                .batch_number("BATCH001")
//                .user_id(101)
//                .notes("Test Notes.")
//                .build();
//
//        transactionTime = LocalDateTime.now().withNano((LocalDateTime.now().getNano() / 100) * 100); // Truncate to microseconds
//        inboundTransaction = new InboundTransaction();
//        inboundTransaction.setId(1);
//        inboundTransaction.setProduct_id(1);
//        inboundTransaction.setQuantity(10);
//        inboundTransaction.setUnit_of_measurement("pond");
//        inboundTransaction.setTotal_cost(10.0);
//        inboundTransaction.setCost_per_unit(10.0 / 10);
//        inboundTransaction.setReceived_date(LocalDate.parse("2024-12-15"));
//        inboundTransaction.setSupplier_contact("820-820-8820");
//        inboundTransaction.setSupplier_name("Test Supplier");
//        inboundTransaction.setSupplier_contact("820-820-8820");
//        inboundTransaction.setBatch_number("BATCH001");
//        inboundTransaction.setTransaction_time(transactionTime);
//        inboundTransaction.setUser_id(101);
//        inboundTransaction.setNotes("Test Notes.");
//
//
//        inboundTransactionList = List.of(inboundTransaction);
//    }
//
//    @Test
//    void testCreateInboundTransaction() throws Exception {
//        // mock the InboundService layer
//        when(inboundService.createInboundTransaction(inboundDTO)).thenReturn(inboundTransaction);
//
//        mockMvc.perform(post("/inbound_transactions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"product_id\": 1, " +
//                                "\"quantity\": 10," +
//                                " \"unit_of_measurement\": \"pond\"," +
//                                " \"total_cost\": 10.0," +
//                                " \"received_date\": \"2024-12-15\"," +
//                                " \"supplier_name\": \"Test Supplier\"," +
//                                " \"supplier_contact\": \"820-820-8820\"," +
//                                " \"batch_number\": \"BATCH001\"," +
//                                " \"user_id\": 101," +
//                                " \"notes\": \"Test Notes.\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(inboundTransaction.getId()))
//                .andExpect(jsonPath("$.product_id").value(inboundTransaction.getProduct_id()))
//                .andExpect(jsonPath("$.quantity").value(inboundTransaction.getQuantity()))
//                .andExpect(jsonPath("$.unit_of_measurement").value(inboundTransaction.getUnit_of_measurement()))
//                .andExpect(jsonPath("$.total_cost").value(inboundTransaction.getTotal_cost()))
//                .andExpect(jsonPath("$.cost_per_unit").value(inboundTransaction.getCost_per_unit()))
//                .andExpect(jsonPath("$.received_date").value(inboundTransaction.getReceived_date().toString()))//
//                .andExpect(jsonPath("$.supplier_name").value(inboundTransaction.getSupplier_name()))
//                .andExpect(jsonPath("$.supplier_contact").value(inboundTransaction.getSupplier_contact()))
//                .andExpect(jsonPath("$.batch_number").value(inboundTransaction.getBatch_number()))
//                //.andExpect(jsonPath("$.transaction_time").value(transactionTime.truncatedTo(ChronoUnit.MICROS).toString()))
//                .andExpect(jsonPath("$.user_id").value(inboundTransaction.getUser_id()))
//                .andExpect(jsonPath("$.notes").value(inboundTransaction.getNotes()));
//    }
//
//    @Test
//    void testGetAllInboundTransactions() throws Exception {
//        when(inboundService.getAllInboundTransactions()).thenReturn(inboundTransactionList);
//
//        mockMvc.perform(get("/inbound_transactions"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(inboundTransaction.getId()))
//                .andExpect(jsonPath("$[0].product_id").value(inboundTransaction.getProduct_id()))
//                .andExpect(jsonPath("$[0].quantity").value(inboundTransaction.getQuantity()))
//                .andExpect(jsonPath("$[0].unit_of_measurement").value(inboundTransaction.getUnit_of_measurement()))
//                .andExpect(jsonPath("$[0].total_cost").value(inboundTransaction.getTotal_cost()))
//                .andExpect(jsonPath("$[0].cost_per_unit").value(inboundTransaction.getCost_per_unit()))
//                .andExpect(jsonPath("$[0].received_date").value(inboundTransaction.getReceived_date().toString()))
//                .andExpect(jsonPath("$[0].supplier_name").value(inboundTransaction.getSupplier_name()))
//                .andExpect(jsonPath("$[0].supplier_contact").value(inboundTransaction.getSupplier_contact()))
//                .andExpect(jsonPath("$[0].batch_number").value(inboundTransaction.getBatch_number()))
//                //.andExpect(jsonPath("$[0].transaction_time").value(transactionTime.truncatedTo(ChronoUnit.MICROS).toString()))
//                .andExpect(jsonPath("$[0].user_id").value(inboundTransaction.getUser_id()))
//                .andExpect(jsonPath("$[0].notes").value(inboundTransaction.getNotes()));
//    }
//
//    @Test
//    void testGetInboundTransactionById() throws Exception {
//        when(inboundService.getInboundTransactionById(1)).thenReturn(inboundTransaction);
//
//        mockMvc.perform(get("/inbound_transactions/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(inboundTransaction.getId()))
//                .andExpect(jsonPath("$.product_id").value(inboundTransaction.getProduct_id()))
//                .andExpect(jsonPath("$.quantity").value(inboundTransaction.getQuantity()))
//                .andExpect(jsonPath("$.unit_of_measurement").value(inboundTransaction.getUnit_of_measurement()))
//                .andExpect(jsonPath("$.total_cost").value(inboundTransaction.getTotal_cost()))
//                .andExpect(jsonPath("$.cost_per_unit").value(inboundTransaction.getCost_per_unit()))
//                .andExpect(jsonPath("$.received_date").value(inboundTransaction.getReceived_date().toString()))
//                .andExpect(jsonPath("$.supplier_name").value(inboundTransaction.getSupplier_name()))
//                .andExpect(jsonPath("$.supplier_contact").value(inboundTransaction.getSupplier_contact()))
//                .andExpect(jsonPath("$.batch_number").value(inboundTransaction.getBatch_number()))
//                //.andExpect(jsonPath("$.transaction_time").value(transactionTime.truncatedTo(ChronoUnit.MICROS).toString()))
//                .andExpect(jsonPath("$.user_id").value(inboundTransaction.getUser_id()))
//                .andExpect(jsonPath("$.notes").value(inboundTransaction.getNotes()));
//    }
//
//}
