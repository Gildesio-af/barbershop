package com.barbeshop.api.dto.product;

import lombok.Builder;

@Builder
public record ProductRequestOrderDTO (
        String id,
        Integer quantity
){
}
