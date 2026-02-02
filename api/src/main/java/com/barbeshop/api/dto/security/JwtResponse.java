package com.barbeshop.api.dto.security;

import lombok.Builder;

@Builder
public record JwtResponse (
        String token,
        String id,
        String username,
        String role,
        String email
) {
}
