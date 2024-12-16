package com.Zi.spring_IMS.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category; //Seafood, Beverages, Equipment

    @Column//(nullable = false)
    private Integer quantity = 0;

    @Column//(nullable = false)
    private String unit_of_measurement;

    @Column(nullable = false)
    private Integer reorder_level; //Add logic to automate reorder suggestions in your application.

    @Column(nullable = false)
    private Integer reorder_quantity;

    @Column//(nullable = false)
    private Double cost_per_unit;

    @Column//(nullable = false)
    private Double total_cost = 0.0;

    @Enumerated(EnumType.STRING)
    private Product_Status status = Product_Status.OUT_OF_STOCK;
}
