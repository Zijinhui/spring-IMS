package com.Zi.spring_IMS.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbounds")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OutboundTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id; // Primary Key

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false) // Foreign Key to Product
//    private Product product;
    @Column(nullable = false)
    private Integer product_id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double cost_per_unit;

    @Column(nullable = false)
    private Double total_cost;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
    @Column(nullable = false)
    private LocalDateTime transaction_time;

    @Column(nullable = false)
    private Integer user_id;

    @Column
    private String notes;

//    @Column(nullable = false)
//    private String status; // (e.g., "Completed", "Pending", "Cancelled")
}
