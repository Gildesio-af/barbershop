package com.barbeshop.api.dto.serviceItem;

import com.barbeshop.api.model.enums.ServiceType;
import lombok.Builder;

@Builder
public record ServiceItemSummaryDTO(
        String id,
        String name,
        String imageUrl,
        Double price,
        ServiceType type
) {
}
