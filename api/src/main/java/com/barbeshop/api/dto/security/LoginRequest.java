package com.barbeshop.api.dto.security;

public record LoginRequest(
        String username,
        String password
) {
}
