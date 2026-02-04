package com.barbeshop.api.dto.security;

import jakarta.validation.constraints.Email;

public record EmailRequestDTO(
        @Email
        String email
) {
}
