package com.barbeshop.api.dto.category;

import lombok.Builder;

@Builder
public record CategoryResponseDTO(
        String id,
        String name
) {
}
