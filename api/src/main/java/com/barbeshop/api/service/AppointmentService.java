package com.barbeshop.api.service;

import com.barbeshop.api.dto.appointment.AppointmentCreateDTO;
import com.barbeshop.api.dto.appointment.AppointmentResponseDTO;
import com.barbeshop.api.model.Appointment;
import com.barbeshop.api.model.Product;
import com.barbeshop.api.model.ServiceItem;
import com.barbeshop.api.model.User;
import com.barbeshop.api.model.enums.AppointmentStatus;
import com.barbeshop.api.repository.AppointmentRepository;
import com.barbeshop.api.repository.ProductRepository;
import com.barbeshop.api.repository.ServiceItemRepository;
import com.barbeshop.api.repository.UserRepository;
import com.barbeshop.api.shared.exception.EntityNotFoundException;
import com.barbeshop.api.shared.utils.AppointmentConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentCreateDTO createDTO, String customerId) {
        User user = userRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("User", customerId));

        List<ServiceItem> products = serviceItemRepository.findAllByIdIn(createDTO.serviceIds());
        List<Product> services = productRepository.findAllByIdIn(createDTO.productsIds());
        double totalPrice = products.stream().mapToDouble(ServiceItem::getPrice).sum();
        totalPrice += services.stream().mapToDouble(Product::getPrice).sum();
        int totalDuration = products.stream().mapToInt(ServiceItem::getDurationInMinutes).sum();

        Appointment newAppointment = Appointment.builder()
                .date(createDTO.appointmentDate())
                .price(totalPrice)
                .durationMinutes(totalDuration)
                .customer(user)
                .status(AppointmentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        appointmentRepository.save(newAppointment);

        return null;
    }
}
