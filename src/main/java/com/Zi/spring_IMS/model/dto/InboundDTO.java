package com.Zi.spring_IMS.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InboundDTO {
    @NotNull(message = "Product ID is required.")
    private Integer product_id;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    private Integer quantity;

    @NotBlank(message = "Unit of measurement is required.")
    private String unit_of_measurement;

    @NotNull(message = "Total cost is required.")
    private Double total_cost;

    @NotNull(message = "Received date is required.")
    private LocalDate received_date;

    @NotBlank(message = "Supplier name is required.")
    @Size(max = 100, message = "Supplier name cannot exceed 100 characters.")
    private String supplier_name;

    @NotBlank(message = "Supplier contact is required.")
    @Size(max = 50, message = "Supplier contact cannot exceed 50 characters.")
    private String supplier_contact;

    @Size(max = 50, message = "Batch number cannot exceed 50 characters.")
    private String batch_number;

    @NotNull(message = "User ID is required.")
    private Integer user_id;

    private String notes;
}
