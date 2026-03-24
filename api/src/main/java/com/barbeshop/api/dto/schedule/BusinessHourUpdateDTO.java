package com.barbeshop.api.dto.schedule;

import com.barbeshop.api.model.enums.OperatingMode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record BusinessHourUpdateDTO(
        Integer dayOfWeek,
        Boolean isOpen,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime openTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime closeTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime breakStart,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime breakEnd,
        OperatingMode operatingMode
) {
}
