package com.Zi.spring_IMS.model.mapper;

import com.Zi.spring_IMS.model.dto.InboundDTO;
import com.Zi.spring_IMS.model.entity.InboundTransaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InboundMapper {

    //Convert InboundDTO to InboundTransaction Entity
    public InboundTransaction toInboundTransactionEntity(InboundDTO inboundDTO) {
        InboundTransaction inboundTransaction = new InboundTransaction();

        inboundTransaction.setProduct_id(inboundDTO.getProduct_id());
        inboundTransaction.setQuantity(inboundDTO.getQuantity());
        inboundTransaction.setUnit_of_measurement(inboundDTO.getUnit_of_measurement());
        inboundTransaction.setTotal_cost(inboundDTO.getTotal_cost());
        // calculate cost per unit
        Double cost_per_unit = inboundTransaction.getTotal_cost() / inboundTransaction.getQuantity();
        inboundTransaction.setCost_per_unit(cost_per_unit);
        inboundTransaction.setReceived_date(inboundDTO.getReceived_date());
        inboundTransaction.setSupplier_name(inboundDTO.getSupplier_name());
        inboundTransaction.setSupplier_contact(inboundDTO.getSupplier_contact());
        inboundTransaction.setBatch_number(inboundDTO.getBatch_number());
        inboundTransaction.setTransaction_time(LocalDateTime.now());
        inboundTransaction.setUser_id(inboundDTO.getUser_id());
        inboundTransaction.setNotes(inboundDTO.getNotes());

        return inboundTransaction;
    }

}
