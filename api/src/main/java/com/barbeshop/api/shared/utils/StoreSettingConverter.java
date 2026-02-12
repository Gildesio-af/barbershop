package com.barbeshop.api.shared.utils;

import com.barbeshop.api.dto.setting.StoreSettingCreateDTO;
import com.barbeshop.api.dto.setting.StoreSettingResponseDTO;
import com.barbeshop.api.dto.setting.StoreSettingUpdateDTO;
import com.barbeshop.api.model.StoreSetting;
import org.springframework.stereotype.Component;

@Component
public class StoreSettingConverter {
    public static StoreSettingResponseDTO modelToResponseDTO(StoreSetting storeSetting) {
        return StoreSettingResponseDTO.builder()
                .id(storeSetting.getId())
                .name(storeSetting.getName())
                .address(storeSetting.getAddress())
                .phone(storeSetting.getPhone())
                .instagram(storeSetting.getInstagram())
                .productsEnabled(storeSetting.getProductsEnabled())
                .build();
    }

    public static StoreSetting createDTOToModel(StoreSettingCreateDTO createDTO) {
        return StoreSetting.builder()
                .name(createDTO.name())
                .address(createDTO.address())
                .phone(createDTO.phone())
                .instagram(createDTO.instagram())
                .productsEnabled(createDTO.productsEnabled() != null ? createDTO.productsEnabled() : true)
                .build();
    }

    public static void update(StoreSetting storeSetting, StoreSettingUpdateDTO updateDTO) {
        if (updateDTO.name() != null) storeSetting.setName(updateDTO.name());
        if (updateDTO.address() != null) storeSetting.setAddress(updateDTO.address());
        if (updateDTO.phone() != null) storeSetting.setPhone(updateDTO.phone());
        if (updateDTO.instagram() != null) storeSetting.setInstagram(updateDTO.instagram());
        if (updateDTO.productsEnabled() != null) storeSetting.setProductsEnabled(updateDTO.productsEnabled());
    }
}
