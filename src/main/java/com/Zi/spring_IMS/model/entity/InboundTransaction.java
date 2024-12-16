package com.Zi.spring_IMS.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="inbounds")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InboundTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private Integer product_id; //given by user(will provide a Product list at front_end)

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String unit_of_measurement;

    @Column(nullable = false)
    private Double total_cost;

    @Column(nullable = false)
    private Double cost_per_unit;

    @Column(nullable = false)
    private LocalDate received_date;

    @Column(nullable = false)
    private String supplier_name;

    @Column(nullable = false)
    private String supplier_contact;

    @Column(nullable = false)
    private String batch_number; //batch identifier for tracking shipments or groups of items

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
    @Column(nullable = false)
    private LocalDateTime transaction_time;

    @Column(nullable = false)
    private Integer user_id;

    @Column
    private String notes;

}
