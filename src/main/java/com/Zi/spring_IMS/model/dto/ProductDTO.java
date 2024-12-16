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
public class ProductDTO {
    // used for create a new Product framework

    @NotBlank //only 1 white space is not allowed
    private String name;

    @NotBlank(message = "Category is required! Please pick one from: Food, Beverages, Equipment")
    @Size(min = 2, max = 100, message = "Product name cannot less than 2 characters and exceed 100 characters")
    private String category;

    @NotNull(message = "Reorder level is required")
    @Min(value = 1, message = " Reorder level must be a t least 1.")
    private Integer reorder_level;

    @NotNull(message = "Reorder quantity is required.")
    private Integer reorder_quantity;
}
