package com.barbeshop.api.controller;

import com.barbeshop.api.dto.appointment.AppointmentCreateDTO;
import com.barbeshop.api.dto.appointment.AppointmentSummaryResponseDTO;
import com.barbeshop.api.dto.schedule.ScheduleSlotDTO;
import com.barbeshop.api.security.CustomUserDetails;
import com.barbeshop.api.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentSummaryResponseDTO> getAppointmentById(@PathVariable String id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<Page<AppointmentSummaryResponseDTO>> getMyAppointments(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(size = 5, sort = "date") Pageable pageable) {
        String customerId = userDetails.getId();
        Page<AppointmentSummaryResponseDTO> appointments = appointmentService.getAppointmentsForCustomer(customerId, pageable);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/availability")
    public ResponseEntity<List<ScheduleSlotDTO>> getAvailability(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "duration", defaultValue = "45") int duration) {
        List<ScheduleSlotDTO> schedule = appointmentService.getDailySchedule(date, duration);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping
    public ResponseEntity<AppointmentSummaryResponseDTO> createAppointment(
            @Valid @RequestBody AppointmentCreateDTO createDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        String customerId = userDetails.getId();
        AppointmentSummaryResponseDTO createdAppointment = appointmentService.createAppointment(createDTO, customerId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAppointment.id())
                .toUri();
        return ResponseEntity.created(location).body(createdAppointment);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(
            @PathVariable String id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<Void> acceptAppointment(
            @PathVariable String id) {
        appointmentService.acceptAppointment(id);
        return ResponseEntity.noContent().build();
    }
}

