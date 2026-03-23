package com.barbeshop.api.dto.appointment;

import com.barbeshop.api.dto.product.ProductRequestOrderDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AppointmentCreateDTO(
        @NotEmpty(message = "Should have at least one service")
        List<@NotBlank(message = "Services ids must not be empty") String> serviceIds,
        List<ProductRequestOrderDTO> products,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime appointmentDate
) {
}
