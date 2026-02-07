package com.barbeshop.api.dto.service;

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
