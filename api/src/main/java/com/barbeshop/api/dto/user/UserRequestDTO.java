package com.barbeshop.api.dto.user;

import com.barbeshop.api.shared.utils.Telephone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserRequestDTO(
        @NotBlank(message = "Username is required")
        @Length(min = 3, max = 100, message = "Username must be between 3 and 50 characters")
        String username,
        @NotBlank(message = "Password is required")
        @Length(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password,
        @Email
        String email,
        @Telephone
        String phoneNumber
) {
}
