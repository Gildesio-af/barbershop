package com.barbeshop.api.dto.appointment;

import com.barbeshop.api.dto.product.ProductSummaryDTO;
import com.barbeshop.api.dto.serviceItem.ServiceItemSummaryDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record AppointmentSummaryResponseDTO(
        String id,
        String customerName,
        String customerNumber,
        String appointmentDate,
        Double totalPrice,
        String status,
        String createdAt,
        List<ServiceItemSummaryDTO> services,
        List<ProductSummaryDTO> products
) {
}
