package com.barbeshop.api.dto.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PasswordUpdateDTO(
        @NotBlank(message = "Current password is required")
        String currentPassword,
        @NotBlank(message = "New password is required")
        @Length(min = 6, max = 20, message = "New password must be between 6 and 20 characters")
        String newPassword
) {
}
