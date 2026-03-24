package com.barbeshop.api.dto.setting;

import com.barbeshop.api.shared.utils.validation.Telephone;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record StoreSettingCreateDTO(
        @NotBlank(message = "Store name is required")
        @Length(min = 3, max = 100, message = "Store name must be between 3 and 100 characters")
        String name,
        @Length(max = 150, message = "Address must be at most 150 characters")
        String address,
        @Telephone(message = "Invalid phone number format")
        String phone,
        String instagram,
        Boolean productsEnabled,
        Boolean isAppointmentRestrictedWeekly,
        @Min(value = 30, message = "Step schedules must be at least 30")
        Integer stepSchedules,
        @Min(value = 5, message = "Max appointments per day must be at least 5")
        Integer pauseTolerance
) {
}
