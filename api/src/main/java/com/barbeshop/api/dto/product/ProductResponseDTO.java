package com.barbeshop.api.dto.product;

import com.barbeshop.api.dto.category.CategoryResponseDTO;
import lombok.Builder;

@Builder
public record ProductResponseDTO(
        String id,
        String name,
        Integer quantity,
        Integer minStock,
        double price,
        String imageUrl,
        boolean isVisible,
        CategoryResponseDTO category
) {
}
