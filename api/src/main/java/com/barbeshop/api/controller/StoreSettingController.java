package com.barbeshop.api.controller;

import com.barbeshop.api.dto.setting.StoreSettingCreateDTO;
import com.barbeshop.api.dto.setting.StoreSettingResponseDTO;
import com.barbeshop.api.dto.setting.StoreSettingUpdateDTO;
import com.barbeshop.api.service.StoreSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class StoreSettingController {
    private final StoreSettingService storeSettingService;

    @GetMapping("/{id}")
    public ResponseEntity<StoreSettingResponseDTO> getStoreSettingById(@PathVariable Integer id) {
        StoreSettingResponseDTO storeSetting = storeSettingService.getStoreSettingById(id);
        return ResponseEntity.ok(storeSetting);
    }

    @PostMapping
    public ResponseEntity<StoreSettingResponseDTO> createStoreSetting(@RequestBody StoreSettingCreateDTO createDTO) {
        StoreSettingResponseDTO createdStoreSetting = storeSettingService.createStoreSetting(createDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStoreSetting.id())
                .toUri();

        return ResponseEntity.created(location).body(createdStoreSetting);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StoreSettingResponseDTO> updateStoreSetting(
            @PathVariable Integer id,
            @RequestBody StoreSettingUpdateDTO updateDTO) {
        StoreSettingResponseDTO updatedStoreSetting = storeSettingService.updateStoreSetting(id, updateDTO);
        return ResponseEntity.ok(updatedStoreSetting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoreSetting(@PathVariable Integer id) {
        storeSettingService.deleteStoreSetting(id);
        return ResponseEntity.noContent().build();
    }
}
