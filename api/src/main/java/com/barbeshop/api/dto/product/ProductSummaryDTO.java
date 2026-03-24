package com.barbeshop.api.dto.product;

import lombok.Builder;

@Builder
public record ProductSummaryDTO(
        String id,
        String name,
        String imageUrl,
        Double price
) {

}
