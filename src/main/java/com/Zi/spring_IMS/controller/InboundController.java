package com.Zi.spring_IMS.controller;

import com.Zi.spring_IMS.model.dto.InboundDTO;
import com.Zi.spring_IMS.model.entity.InboundTransaction;
import com.Zi.spring_IMS.service.InboundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inbound_transactions")
public class InboundController {
    private final InboundService inboundService;

    @Autowired
    public InboundController(InboundService inboundService) {
        this.inboundService = inboundService;
    }

    @PostMapping
    public ResponseEntity<InboundTransaction> createInboundTransaction(@Valid @RequestBody InboundDTO inboundDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField() + " - " + error.getRejectedValue());
            }
            throw new RuntimeException("Bad Request!");
        }

        return ResponseEntity.ok(inboundService.createInboundTransaction(inboundDTO));
    }

    @GetMapping
    public ResponseEntity<List<InboundTransaction>> getAllInboundTransactions() {
        return ResponseEntity.ok(inboundService.getAllInboundTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InboundTransaction> getInboundTransactionById(@PathVariable Integer id) { return  ResponseEntity.ok(inboundService.getInboundTransactionById(id)); }

    // Consider to allow users update Transaction status from Completed to Canceled if any exist transaction record has mistakes

//    @PutMapping
//    public ResponseEntity<InboundTransaction> updateInboundTransaction(@Valid @RequestBody InboundDTO inboundDTO, BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                System.out.println(error.getField() + " - " + error.getRejectedValue());
//            }
//            throw new RuntimeException("Bad Request!");
//        }
//
//        return ResponseEntity.ok(inboundService.updateInboundTransaction(inboundDTO));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Boolean> deleteInboundTransaction(@PathVariable Integer id) { return ResponseEntity.ok(inboundService.deleteInboundTransaction(id)); }

}
