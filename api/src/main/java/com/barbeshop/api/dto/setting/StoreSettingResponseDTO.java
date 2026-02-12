package com.barbeshop.api.dto.setting;

import lombok.Builder;

@Builder
public record StoreSettingResponseDTO(
        Integer id,
        String name,
        String address,
        String phone,
        String instagram,
        Boolean productsEnabled) {
}
