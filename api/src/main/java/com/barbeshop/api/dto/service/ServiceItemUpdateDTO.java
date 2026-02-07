package com.barbeshop.api.dto.service;

import com.barbeshop.api.model.enums.ServiceType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record ServiceItemUpdateDTO(
        @Length(min = 3, max = 50, message = "Service name must be between 3 and 50 characters")
        String name,
        ServiceType type,
        @Positive(message = "Duration must be a positive integer")
        Integer durationInMinutes,
        @PositiveOrZero(message = "Price must be a positive number or zero")
        Double price,
        String imageUrl,
        Boolean isVisible
) {
}
