package com.barbeshop.api.dto.schedule;

import java.time.LocalDateTime;

public record ScheduleSlotDTO(LocalDateTime time, boolean available) {
}

