package com.barbeshop.api.controller;

import com.barbeshop.api.dto.schedule.BusinessHourResponseDTO;
import com.barbeshop.api.dto.schedule.BusinessHourUpdateDTO;
import com.barbeshop.api.service.BusinessHourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business-hours")
@RequiredArgsConstructor
public class BusinessHourController {
    private final BusinessHourService businessHourService;

    @GetMapping("/{storeSettingsId}")
    public ResponseEntity<List<BusinessHourResponseDTO>> getBusinessHoursByStoreSettings(@PathVariable Integer storeSettingsId) {
        List<BusinessHourResponseDTO> businessHours = businessHourService.getAllBusinessHoursByStoreSettings(storeSettingsId);
        return ResponseEntity.ok(businessHours);
    }

    @PatchMapping("/{storeSettingsId}")
    public ResponseEntity<List<BusinessHourResponseDTO>> updateBusinessHours(@PathVariable Integer storeSettingsId,
                                                                             @RequestBody List<BusinessHourUpdateDTO> updateDTOs) {
        List<BusinessHourResponseDTO> updatedBusinessHours = businessHourService.updateBusinessHours(storeSettingsId, updateDTOs);
        return ResponseEntity.ok(updatedBusinessHours);
    }
}
