package com.barbeshop.api.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AppointmentCreateDTO(
        @NotEmpty(message = "Should have at least one service")
        List<@NotBlank(message = "Services ids must not be empty") String> serviceIds,
        List<@NotBlank(message = "Products ids must not be empty") String> productsIds,
        LocalDateTime appointmentDate
) {
}
