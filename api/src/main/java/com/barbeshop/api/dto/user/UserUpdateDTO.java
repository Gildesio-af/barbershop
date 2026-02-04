package com.barbeshop.api.dto.user;

import com.barbeshop.api.shared.utils.Telephone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO (
        @Size(min = 3, max = 100 , message = "Username must be between 3 and 100 characters")
        String username,
        @Email(message = "Invalid email format")
        String email,
        @Telephone
        String phoneNumber
){
}
