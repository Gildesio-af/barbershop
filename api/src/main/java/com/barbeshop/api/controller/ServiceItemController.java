package com.barbeshop.api.controller;

import com.barbeshop.api.dto.service.ServiceItemCreateDTO;
import com.barbeshop.api.dto.service.ServiceItemResponseDTO;
import com.barbeshop.api.dto.service.ServiceItemUpdateDTO;
import com.barbeshop.api.model.enums.ServiceType;
import com.barbeshop.api.service.ServiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceItemController {
    private final ServiceItemService serviceItemService;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceItemResponseDTO> getServiceItemById(@PathVariable String id) {
        ServiceItemResponseDTO serviceItem = serviceItemService.findById(id);
        return ResponseEntity.ok(serviceItem);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ServiceItemResponseDTO>> getAllServiceItems(@PageableDefault Pageable pageable) {
        Page<ServiceItemResponseDTO> serviceItems = serviceItemService.findAll(pageable);
        return ResponseEntity.ok(serviceItems);
    }

    @GetMapping("/type/{serviceType}")
    public ResponseEntity<Page<ServiceItemResponseDTO>> getServiceItemsByType(
            @PathVariable String serviceType,
            @PageableDefault Pageable pageable) {
        Page<ServiceItemResponseDTO> serviceItems = serviceItemService.findAllByServiceType(serviceType, pageable);
        return ResponseEntity.ok(serviceItems);
    }

    @PostMapping
    public ResponseEntity<ServiceItemResponseDTO> createServiceItem(
            @RequestBody ServiceItemCreateDTO createDTO) {
        ServiceItemResponseDTO createdServiceItem = serviceItemService.createServiceItem(createDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdServiceItem.id())
                .toUri();

        return ResponseEntity.created(location).body(createdServiceItem);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ServiceItemResponseDTO> updateServiceItem(
            @PathVariable String id,
            @RequestBody ServiceItemUpdateDTO updateDTO) {
        ServiceItemResponseDTO updatedServiceItem = serviceItemService.updateServiceItem(id, updateDTO);
        return ResponseEntity.ok(updatedServiceItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceItem(@PathVariable String id) {
        serviceItemService.deleteServiceItem(id);
        return ResponseEntity.noContent().build();
    }
}
