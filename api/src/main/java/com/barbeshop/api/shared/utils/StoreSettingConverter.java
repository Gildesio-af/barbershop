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
                .isAppointmentsWeekly(storeSetting.getIsAppointmentsWeekly())
                .stepSchedule(storeSetting.getStepSchedule())
                .pauseTolerance(storeSetting.getPauseTolerance())
                .build();
    }

    public static StoreSetting createDTOToModel(StoreSettingCreateDTO createDTO) {
        return StoreSetting.builder()
                .name(createDTO.name())
                .address(createDTO.address())
                .phone(createDTO.phone())
                .instagram(createDTO.instagram())
                .productsEnabled(createDTO.productsEnabled() != null ? createDTO.productsEnabled() : true)
                .isAppointmentsWeekly(createDTO.isAppointmentRestrictedWeekly() != null ? createDTO.isAppointmentRestrictedWeekly() : false)
                .stepSchedule(createDTO.stepSchedules() != null ? createDTO.stepSchedules() : 45)
                .pauseTolerance(createDTO.pauseTolerance() != null ? createDTO.pauseTolerance() : 15)
                .build();
    }

    public static void update(StoreSetting storeSetting, StoreSettingUpdateDTO updateDTO) {
        if (updateDTO.name() != null) storeSetting.setName(updateDTO.name());
        if (updateDTO.address() != null) storeSetting.setAddress(updateDTO.address());
        if (updateDTO.phone() != null) storeSetting.setPhone(updateDTO.phone());
        if (updateDTO.instagram() != null) storeSetting.setInstagram(updateDTO.instagram());
        if (updateDTO.productsEnabled() != null) storeSetting.setProductsEnabled(updateDTO.productsEnabled());
        if (updateDTO.stepSchedules() != null) storeSetting.setStepSchedule(updateDTO.stepSchedules());
        if (updateDTO.pauseTolerance() != null) storeSetting.setPauseTolerance(updateDTO.pauseTolerance());
        if (updateDTO.isAppointmentRestrictedWeekly() != null) storeSetting.setIsAppointmentsWeekly(updateDTO.isAppointmentRestrictedWeekly());
    }
}
