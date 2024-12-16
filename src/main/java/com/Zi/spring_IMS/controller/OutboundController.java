package com.Zi.spring_IMS.controller;

import com.Zi.spring_IMS.model.dto.OutboundDTO;
import com.Zi.spring_IMS.model.entity.OutboundTransaction;
import com.Zi.spring_IMS.service.OutboundService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("outbound_transactions")
public class OutboundController {
    private final OutboundService outboundService;

    public OutboundController(OutboundService outboundService) { this.outboundService = outboundService;}

    @PostMapping
    public ResponseEntity<OutboundTransaction> createOutboundTransaction(@Valid @RequestBody OutboundDTO outboundDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField() + " - " + error.getRejectedValue());
            }
            throw new RuntimeException("Bad Request!");
        }

        return ResponseEntity.ok(outboundService.createOutboundTransaction(outboundDTO));
    }

    @GetMapping
    public ResponseEntity<List<OutboundTransaction>> getAllOutboundTransaction() { return ResponseEntity.ok(outboundService.getAllOutboundTransaction()); }

    @GetMapping("/{id}")
    public ResponseEntity<OutboundTransaction> getOutboundTransactionById(@PathVariable Integer id) { return ResponseEntity.ok(outboundService.getOutBoundTransactionById(id)); }
}
