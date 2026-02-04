package com.barbeshop.api.dto.user;

import lombok.Builder;

@Builder
public record UserResponseDTO(
        String id,
        String username,
        String email,
        String phoneNumber,
        Integer loyaltyPoints,
        String roleName,
        String createdAt
) {
}
