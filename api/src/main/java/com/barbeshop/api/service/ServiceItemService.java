package com.barbeshop.api.service;

import com.barbeshop.api.dto.service.ServiceItemCreateDTO;
import com.barbeshop.api.dto.service.ServiceItemResponseDTO;
import com.barbeshop.api.dto.service.ServiceItemUpdateDTO;
import com.barbeshop.api.model.ServiceItem;
import com.barbeshop.api.model.enums.ServiceType;
import com.barbeshop.api.repository.ServiceItemRepository;
import com.barbeshop.api.shared.exception.EntityNotFoundException;
import com.barbeshop.api.shared.utils.ServiceItemConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceItemService {
    private final ServiceItemRepository serviceRepository;

    public ServiceItemResponseDTO findById(String id) {
        return serviceRepository.findById(id).map(ServiceItemConverter::modelToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Service item not found with id: " + id));
    }

    public Page<ServiceItemResponseDTO> findAll(Pageable pageable) {
        return serviceRepository.findAll(pageable).map(ServiceItemConverter::modelToResponseDTO);
    }

    public Page<ServiceItemResponseDTO> findAllByServiceType(String serviceType, Pageable pageable) {
        ServiceType type = ServiceType.toEnum(serviceType);
        return serviceRepository.findAllByType(type, pageable)
                .map(ServiceItemConverter::modelToResponseDTO);
    }

    @Transactional
    public ServiceItemResponseDTO createServiceItem(ServiceItemCreateDTO createDTO) {
        ServiceItem serviceItem = ServiceItemConverter.createDTOTomModel(createDTO);
        return ServiceItemConverter.modelToResponseDTO(serviceRepository.save(serviceItem));
    }

    @Transactional
    public ServiceItemResponseDTO updateServiceItem(String id, ServiceItemUpdateDTO updateDTO) {
        ServiceItem serviceItem = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service item not found with id: " + id));

        ServiceItemConverter.updateModelFromDTO(serviceItem, updateDTO);
        return ServiceItemConverter.modelToResponseDTO(serviceRepository.save(serviceItem));
    }

    @Transactional
    public void deleteServiceItem(String id) {
        int rowsAffected = serviceRepository.softDeleteById(id);
        if (rowsAffected == 0)
            throw new EntityNotFoundException("Service item", id);
    }
}
