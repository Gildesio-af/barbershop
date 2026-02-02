package com.barbeshop.api.dto.error;

public record ErrorResponse(
        String message,
        String field
) {
}
