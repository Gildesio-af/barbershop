package com.barbeshop.api.service;

import com.barbeshop.api.dto.setting.StoreSettingCreateDTO;
import com.barbeshop.api.dto.setting.StoreSettingResponseDTO;
import com.barbeshop.api.dto.setting.StoreSettingUpdateDTO;
import com.barbeshop.api.model.StoreSetting;
import com.barbeshop.api.repository.StoreSettingRepository;
import com.barbeshop.api.shared.exception.EntityNotFoundException;
import com.barbeshop.api.shared.utils.StoreSettingConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreSettingService {
    private final StoreSettingRepository storeSettingRepository;

    @Transactional(readOnly = true)
    public StoreSettingResponseDTO getStoreSettingById(Integer id) {
        return storeSettingRepository.findById(id)
                .map(StoreSettingConverter::modelToResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("StoreSetting", String.valueOf(id)));
    }

    public StoreSettingResponseDTO createStoreSetting(StoreSettingCreateDTO createDTO) {
        StoreSetting storeSetting = StoreSettingConverter.createDTOToModel(createDTO);

        return StoreSettingConverter.modelToResponseDTO(storeSettingRepository.save(storeSetting));
    }

    public StoreSettingResponseDTO updateStoreSetting(Integer id, StoreSettingUpdateDTO updateDTO) {
        StoreSetting storeSetting = storeSettingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StoreSetting", String.valueOf(id)));

        StoreSettingConverter.update(storeSetting, updateDTO);

        return StoreSettingConverter.modelToResponseDTO(storeSettingRepository.save(storeSetting));
    }

    public void deleteStoreSetting(Integer id) {
        if (!storeSettingRepository.existsById(id))
            throw new EntityNotFoundException("StoreSetting", String.valueOf(id));

        storeSettingRepository.deleteById(id);
    }
}
