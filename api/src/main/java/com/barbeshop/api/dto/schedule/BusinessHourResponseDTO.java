package com.barbeshop.api.dto.schedule;

import com.barbeshop.api.model.enums.OperatingMode;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record BusinessHourResponseDTO(
        Integer id,
        String dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        LocalTime breakStartTime,
        LocalTime breakEndTime,
        OperatingMode operatingMode,
        Boolean isOpen
) {
}
