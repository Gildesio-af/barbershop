package com.barbeshop.api.dto.product;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record ProductUpdateDTO(
            @Length(min = 3, max = 50, message = "Product name must be between 3 and 50 characters")
            String name,
            @PositiveOrZero(message = "Quantity cannot be negative")
            Integer quantity,
            @PositiveOrZero(message = "Minimum stock must be positive")
            Integer minStock,
            @PositiveOrZero(message = "Price must be positive")
            Double price,
            String imageUrl,
            String categoryId,
            Boolean isVisible
) {
}
