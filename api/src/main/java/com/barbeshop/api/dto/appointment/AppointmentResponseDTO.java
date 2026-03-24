package com.barbeshop.api.dto.appointment;

import com.barbeshop.api.dto.product.ProductResponseDTO;
import com.barbeshop.api.dto.serviceItem.ServiceItemResponseDTO;
import com.barbeshop.api.model.enums.AppointmentStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AppointmentResponseDTO(
        String id,
        String userId,
        String customerName,
        LocalDateTime appointmentDate,
        Integer durationMinutes,
        Double totalPrice,
        AppointmentStatus status,
        LocalDateTime createdAt,
        List<ServiceItemResponseDTO> services,
        List<ProductResponseDTO> products
) {
}
