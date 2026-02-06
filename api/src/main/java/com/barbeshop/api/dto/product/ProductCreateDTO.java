package com.barbeshop.api.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record ProductCreateDTO (
        @NotBlank(message = "Product name cannot be blank")
        @Length(max = 50, message = "Product name cannot exceed 50 characters")
        String name,
        @NotNull(message = "Quantity cannot be null")
        @PositiveOrZero(message = "Quantity cannot be negative")
        Integer quantity,
        @NotNull(message = "Minimum stock cannot be null")
        @PositiveOrZero(message = "Minimum stock cannot be negative")
        Integer minStock,
        @NotNull(message = "Price cannot be null")
        @PositiveOrZero(message = "Price cannot be negative")
        double price,
        String categoryName,
        String imageUrl,
        boolean isVisible
){
}
