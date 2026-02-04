package com.barbeshop.api.dto.security;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PasswordRequestDTO(
        @NotBlank(message = "Password is required")
        @Length(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password
) {
}
