package com.barbeshop.api.shared.utils;


import com.barbeshop.api.dto.appointment.AppointmentCreateDTO;
import com.barbeshop.api.dto.appointment.AppointmentSummaryResponseDTO;
import com.barbeshop.api.model.Appointment;
import com.barbeshop.api.model.AppointmentProduct;
import com.barbeshop.api.model.AppointmentServiceItem;
import com.barbeshop.api.model.User;
import com.barbeshop.api.model.enums.AppointmentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AppointmentConverter {

    public static Appointment createDTOToEntity(AppointmentCreateDTO appointment, User customer, Double totalPrice, Integer totalDuration) {
        return Appointment.builder()
                .date(appointment.appointmentDate())
                .price(totalPrice)
                .durationMinutes(totalDuration)
                .customer(customer)
                .status(AppointmentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AppointmentSummaryResponseDTO toSummaryDTO (Appointment appointment) {
        var productsSummary = appointment.getProducts().stream()
                .map(AppointmentProduct::getProduct)
                .map(ProductConverter::toSummaryDTO)
                .toList();

        var servicesSummary = appointment.getServices().stream()
                .map(AppointmentServiceItem::getService)
                .map(ServiceItemConverter::toSummaryDTO)
                .toList();

        return AppointmentSummaryResponseDTO
                .builder()
                .id(appointment.getId())
                .customerName(appointment.getCustomer().getUsername())
                .customerNumber(appointment.getCustomer().getPhoneNumber())
                .appointmentDate(appointment.getDate().toString())
                .totalPrice(appointment.getPrice())
                .status(appointment.getStatus().name())
                .createdAt(appointment.getCreatedAt().toString())
                .services(servicesSummary)
                .products(productsSummary)
                .build();
    }

}
