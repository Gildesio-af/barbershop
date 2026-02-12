package com.barbeshop.api.shared.utils;

import com.barbeshop.api.dto.schedule.BusinessHourResponseDTO;
import com.barbeshop.api.dto.schedule.BusinessHourUpdateDTO;
import com.barbeshop.api.model.BusinessHour;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BusinessHourConverter {
    public static BusinessHourResponseDTO modelToDTO(BusinessHour businessHour) {
        return BusinessHourResponseDTO.builder()
                .id(businessHour.getId())
                .dayOfWeek(getDayOfWeekName(businessHour.getDayOfWeek()))
                .openTime(businessHour.getOpenTime())
                .closeTime(businessHour.getCloseTime())
                .breakStartTime(businessHour.getBreakStartTime())
                .breakEndTime(businessHour.getBreakEndTime())
                .operatingMode(businessHour.getOperatingMode())
                .isOpen(businessHour.getIsOpen())
                .build();
    }

    public static void update(BusinessHour businessHour, BusinessHourUpdateDTO updateDTO) {
        if (updateDTO.isOpen() != null) businessHour.setIsOpen(updateDTO.isOpen());
        if (updateDTO.openTime() != null) businessHour.setOpenTime(updateDTO.openTime());
        if (updateDTO.closeTime() != null) businessHour.setCloseTime(updateDTO.closeTime());
        if (updateDTO.operatingMode() != null) businessHour.setOperatingMode(updateDTO.operatingMode());

        businessHour.setBreakStartTime(updateDTO.breakStart());
        businessHour.setBreakEndTime(updateDTO.breakEnd());
    }

    private static String getDayOfWeekName(Integer dayOfWeek) {
        return switch (dayOfWeek) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            case 7 -> "Sunday";
            default -> throw new IllegalArgumentException("Invalid day of week: " + dayOfWeek);
        };
    }
}
