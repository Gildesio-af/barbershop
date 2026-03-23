package com.barbeshop.api.dto.serviceItem;

import com.barbeshop.api.model.enums.ServiceType;
import lombok.Builder;

@Builder
public record ServiceItemResponseDTO(
        String id,
        String name,
        ServiceType type,
        Integer durationInMinutes,
        Double price,
        String imageUrl,
        Boolean isVisible
){
}
