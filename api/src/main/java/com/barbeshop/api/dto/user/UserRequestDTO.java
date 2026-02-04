package com.barbeshop.api.dto.user;

public record UserRequestDTO(
        String username,
        String password,
        String email,
        String phoneNumber
) {
}
