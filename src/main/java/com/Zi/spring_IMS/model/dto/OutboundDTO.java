package com.Zi.spring_IMS.model.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutboundDTO {
    @NotNull(message = "Product ID is required.")
    private Integer product_id;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    private Integer quantity;

    @NotNull(message = "User ID is required.")
    private Integer user_id;

    @Size(max = 200, message = "Notes cannot exceed 200 characters.")
    private String notes;
}
