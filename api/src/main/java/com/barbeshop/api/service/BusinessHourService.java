package com.barbeshop.api.service;

import com.barbeshop.api.dto.schedule.BusinessHourResponseDTO;
import com.barbeshop.api.dto.schedule.BusinessHourUpdateDTO;
import com.barbeshop.api.model.BusinessHour;
import com.barbeshop.api.repository.BusinessHourRepository;
import com.barbeshop.api.shared.utils.BusinessHourConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessHourService {
    private final BusinessHourRepository businessHourRepository;

    public List<BusinessHourResponseDTO> getAllBusinessHoursByStoreSettings(Integer storeSettingsId) {
        return businessHourRepository.findByStoreSettingIdOrderByDayOfWeek(storeSettingsId).stream()
                .map(BusinessHourConverter::modelToDTO)
                .toList();
    }

    public List<BusinessHourResponseDTO> updateBusinessHours(Integer storeSettingsId, List<BusinessHourUpdateDTO> updateDTOs) {
        List<BusinessHour> businessHours = businessHourRepository.findByStoreSettingIdOrderByDayOfWeek(storeSettingsId);

        Map<Integer, BusinessHourUpdateDTO> dtosMap = updateDTOs.stream()
                .collect(Collectors.toMap(BusinessHourUpdateDTO::dayOfWeek, Function.identity()));

        businessHours.forEach(bh -> {
            BusinessHourUpdateDTO dto = dtosMap.get(bh.getDayOfWeek());

            if (dto != null) {
                BusinessHourConverter.update(bh, dto);
            }
        });

        List<BusinessHour> saved = businessHourRepository.saveAll(businessHours);

        return saved.stream()
                .map(BusinessHourConverter::modelToDTO)
                .toList();
    }
}
