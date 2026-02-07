package com.barbeshop.api.shared.utils;

import com.barbeshop.api.dto.service.ServiceItemCreateDTO;
import com.barbeshop.api.dto.service.ServiceItemResponseDTO;
import com.barbeshop.api.dto.service.ServiceItemUpdateDTO;
import com.barbeshop.api.model.ServiceItem;
import org.springframework.stereotype.Component;

@Component
public class ServiceItemConverter {
    public static ServiceItemResponseDTO modelToResponseDTO(ServiceItem service) {
        return ServiceItemResponseDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .type(service.getType())
                .durationInMinutes(service.getDurationInMinutes())
                .price(service.getPrice())
                .imageUrl(service.getImageUrl())
                .isVisible(service.isVisible())
                .build();
    }

    public static ServiceItem createDTOTomModel(ServiceItemCreateDTO createDTO) {
        return ServiceItem.builder()
                .name(createDTO.name())
                .type(createDTO.type())
                .durationInMinutes(createDTO.durationInMinutes())
                .price(createDTO.price())
                .imageUrl(createDTO.imageUrl())
                .isVisible(createDTO.isVisible())
                .build();
    }

    public static void updateModelFromDTO(ServiceItem service, ServiceItemUpdateDTO updateDTO) {
        if(updateDTO.name() != null) service.setName(updateDTO.name());
        if(updateDTO.type() != null) service.setType(updateDTO.type());
        if(updateDTO.durationInMinutes() != null) service.setDurationInMinutes(updateDTO.durationInMinutes());
        if(updateDTO.price() != null) service.setPrice(updateDTO.price());
        if(updateDTO.imageUrl() != null) service.setImageUrl(updateDTO.imageUrl());
        if(updateDTO.isVisible() != null) service.setVisible(updateDTO.isVisible());
    }
}
