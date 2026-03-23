package com.barbeshop.api.service;

import com.barbeshop.api.dto.appointment.AppointmentCreateDTO;
import com.barbeshop.api.dto.appointment.AppointmentSummaryResponseDTO;
import com.barbeshop.api.dto.product.ProductRequestOrderDTO;
import com.barbeshop.api.dto.schedule.ScheduleSlotDTO;
import com.barbeshop.api.model.*;
import com.barbeshop.api.repository.*;
import com.barbeshop.api.shared.exception.EntityNotFoundException;
import com.barbeshop.api.shared.exception.ScheduleNotAvailableException;
import com.barbeshop.api.shared.utils.AppointmentConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
//TODO: Add method to let customers know if their appointment was accepted or not, and to let them know if it was canceled by the shop.
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final ProductRepository productRepository;
    private final BusinessHourRepository businessHourRepository;
    private final StoreSettingRepository storeSettingRepository;

    public AppointmentSummaryResponseDTO getAppointmentById(String appointmentId) {
        Appointment appointment = appointmentRepository.findDetailedById(appointmentId).orElseThrow(
                () -> new EntityNotFoundException("Appointment", appointmentId)
        );

        return AppointmentConverter.toSummaryDTO(appointment);
    }

    public Page<AppointmentSummaryResponseDTO> getAppointmentsForCustomer(String customerId, Pageable pageable) {
        Page<Appointment> appointmentsPage = appointmentRepository.findAllByCustomerId(customerId, pageable);
        return appointmentsPage.map(AppointmentConverter::toSummaryDTO);
    }

    public List<ScheduleSlotDTO> getDailySchedule(LocalDate date, int serviceDuration) {
        StoreSetting storeSetting = storeSettingRepository.findTopByOrderByIdAsc()
                .orElseThrow(() -> new EntityNotFoundException("StoreSetting", "Barbershop without any store settings"));

        int dayOfWeekValue = date.getDayOfWeek().getValue();
        List<BusinessHour> businessHours = businessHourRepository.findByStoreSettingIdOrderByDayOfWeek(storeSetting.getId());

        Optional<BusinessHour> businessHourOpt = businessHours.stream()
                .filter(bh -> bh.getDayOfWeek() == dayOfWeekValue)
                .findFirst();

        if (businessHourOpt.isEmpty()) return Collections.emptyList();

        BusinessHour businessHour = businessHourOpt.get();
        if (Boolean.FALSE.equals(businessHour.getIsOpen())) return Collections.emptyList();

        List<Appointment> existingAppointments = appointmentRepository.findActiveAppointmentsByDateRange(
                date.atStartOfDay(), date.atTime(LocalTime.MAX));

        List<ScheduleSlotDTO> slots = new ArrayList<>();

        int step = storeSetting.getStepSchedule() != null ? storeSetting.getStepSchedule() : 45;
        int pauseTolerance = storeSetting.getPauseTolerance() != null ? storeSetting.getPauseTolerance() : 30;

        LocalDateTime cursor = date.atTime(businessHour.getOpenTime());
        LocalDateTime closeTime = date.atTime(businessHour.getCloseTime());

        LocalDateTime breakStart = businessHour.getBreakStartTime() != null ? date.atTime(businessHour.getBreakStartTime()) : null;
        LocalDateTime breakEnd = businessHour.getBreakEndTime() != null ? date.atTime(businessHour.getBreakEndTime()) : null;

        while (cursor.plusMinutes(pauseTolerance).isBefore(closeTime) || cursor.plusMinutes(pauseTolerance).isEqual(closeTime)) {

            if (breakStart != null && breakEnd != null) {
                if (cursor.isBefore(breakEnd) && cursor.plusMinutes(pauseTolerance).isAfter(breakStart)) {
                    cursor = breakEnd;
                    continue;
                }
            }

            LocalDateTime end = cursor.plusMinutes(serviceDuration);
            final LocalDateTime currentCursor = cursor;

            Optional<Appointment> overlappingAppt = existingAppointments.stream()
                    .filter(appt -> isOverlapping(currentCursor, end, appt))
                    .min(Comparator.comparing(Appointment::getDate));

            if (overlappingAppt.isPresent()) {
                Appointment appt = overlappingAppt.get();

                LocalDateTime apptEnd = appt.getDate().plusMinutes(appt.getDurationMinutes());

                slots.add(new ScheduleSlotDTO(cursor, false));

                if (apptEnd.isAfter(cursor)) {
                    cursor = apptEnd;
                } else {
                    cursor = cursor.plusMinutes(step);
                }
                continue;
            }
            boolean isAvailable = validateBusinessBounds(cursor, pauseTolerance, businessHour);

            if (cursor.isBefore(LocalDateTime.now())) isAvailable = false;

            slots.add(new ScheduleSlotDTO(cursor, isAvailable));

            cursor = cursor.plusMinutes(step);
        }

        return slots;
    }

    @Transactional
    public AppointmentSummaryResponseDTO acceptAppointment(String appointmentId) {
        boolean exists = appointmentRepository.existsById(appointmentId);
        if (!exists)
            throw new EntityNotFoundException("Appointment", appointmentId);

        if (appointmentRepository.acceptAppointmentById(appointmentId) <= 0)
            throw new IllegalStateException("Failed to accept the appointment. It may have already been accepted or canceled");

        return getAppointmentById(appointmentId);
    }

    @Transactional
    public void cancelAppointment(String appointmentId) {
        boolean exists = appointmentRepository.existsById(appointmentId);
        if (!exists)
            throw new EntityNotFoundException("Appointment", appointmentId);

        if (appointmentRepository.cancelAppointmentById(appointmentId) <= 0)
            throw new IllegalStateException("Failed to cancel the appointment. It may have already been canceled");
    }

    @Transactional
    public AppointmentSummaryResponseDTO createAppointment(AppointmentCreateDTO createDTO, String customerId) {
        User user = userRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("User", customerId));

        StoreSetting storeSetting = storeSettingRepository.findTopByOrderByIdAsc()
                .orElseThrow(() -> new EntityNotFoundException("StoreSetting", "Barbershop without any store settings"));

        int dayOfWeekValue = createDTO.appointmentDate().getDayOfWeek().getValue();

        List<BusinessHour> businessHours = businessHourRepository.findByStoreSettingIdOrderByDayOfWeek(storeSetting.getId());
        BusinessHour businessHour = businessHours.stream()
                .filter(bh -> bh.getDayOfWeek() == dayOfWeekValue)
                .findFirst()
                .orElseThrow(() -> new ScheduleNotAvailableException("The shop is closed on this day."));

        List<String> serviceIds = createDTO.serviceIds() != null ? createDTO.serviceIds() : Collections.emptyList();
        List<ProductRequestOrderDTO> productRequests = createDTO.products() != null ? createDTO.products() : Collections.emptyList();

        if (serviceIds.isEmpty())
            throw new IllegalArgumentException("At least one service must be selected.");

        validateCurrentWeekLimit(createDTO.appointmentDate(), storeSetting.getIsAppointmentsWeekly());

        List<ServiceItem> services = serviceItemRepository.findAllByIdIn(serviceIds);
        if (services.size() != new HashSet<>(serviceIds).size())
            throw new EntityNotFoundException("One or more services provided do not exist.");

        Map<String, Integer> productQuantityMap = productRequests.stream()
                .collect(Collectors.toMap(ProductRequestOrderDTO::id, ProductRequestOrderDTO::quantity));

        List<String> productIds = new ArrayList<>(productQuantityMap.keySet());
        List<Product> products = productRepository.findAllByIdInList(productIds);

        if (!productIds.isEmpty() && products.size() != productIds.size())
            throw new EntityNotFoundException("One or more products provided do not exist.");

        double totalPrice = 0.0;

        for (Product product : products) {
            Integer requestedQuantity = productQuantityMap.get(product.getId());
            if (product.getQuantity() < requestedQuantity) {
                throw new IllegalArgumentException("Requested quantity for product " + product.getName() + " exceeds available stock.");
            }
            totalPrice += product.getPrice() * requestedQuantity;
        }

        totalPrice += services.stream().mapToDouble(ServiceItem::getPrice).sum();
        int totalDuration = services.stream().mapToInt(ServiceItem::getDurationInMinutes).sum();

        LocalDateTime appointmentDate = createDTO.appointmentDate();
        LocalDateTime appointmentEnd = appointmentDate.plusMinutes(totalDuration);

        if (!validateBusinessBounds(appointmentDate, totalDuration, businessHour))
            throw new ScheduleNotAvailableException("Appointment time is outside business hours or during break.");

        if (appointmentRepository.existsOverlappingAppointment(appointmentDate, appointmentEnd) > 0L)
            throw new ScheduleNotAvailableException("The selected date and time is not available for the appointment.");

        if (appointmentRepository.verifyIfCustomerHasPendingAppointments(customerId) > 0L)
            throw new ScheduleNotAvailableException("The customer has pending appointments. Please complete or cancel them before creating a new one.");

        Appointment newAppointment = AppointmentConverter
                .createDTOToEntity(createDTO, user, totalPrice, totalDuration);

        Set<AppointmentProduct> productsToSave = products.stream()
                .map(product -> AppointmentProduct.builder()
                        .product(product)
                        .appointment(newAppointment)
                        .priceAtMoment(product.getPrice())
                        .quantity(productQuantityMap.get(product.getId()))
                        .build())
                .collect(Collectors.toSet());

        Set<AppointmentServiceItem> servicesToSave = services.stream()
                .map(service -> AppointmentServiceItem.builder()
                        .service(service)
                        .appointment(newAppointment)
                        .priceAtMoment(service.getPrice())
                        .build())
                .collect(Collectors.toSet());

        newAppointment.setProducts(productsToSave);
        newAppointment.setServices(servicesToSave);

        Appointment appointmentSaved = appointmentRepository.save(newAppointment);

        return AppointmentConverter.toSummaryDTO(appointmentSaved);
    }

    private boolean validateBusinessBounds(LocalDateTime start, int durationMinutes, BusinessHour businessHour) {
        int gracePeriod = 15;

        int storeCheckDuration = Math.max(0, durationMinutes - gracePeriod);

        LocalDateTime end = start.plusMinutes(storeCheckDuration);

        if (!start.toLocalDate().equals(end.toLocalDate())) return false;

        LocalTime endTimeOfDay = end.toLocalTime();
        LocalTime startTimeOfDay = start.toLocalTime();

        if (startTimeOfDay.isBefore(businessHour.getOpenTime()) || endTimeOfDay.isAfter(businessHour.getCloseTime())) {
            return false;
        }

        if (businessHour.getBreakStartTime() != null && businessHour.getBreakEndTime() != null) {
            boolean overlapsBreak = startTimeOfDay.isBefore(businessHour.getBreakEndTime())
                    && endTimeOfDay.isAfter(businessHour.getBreakStartTime());
            return !overlapsBreak;
        }
        return true;
    }

    private boolean isOverlapping(LocalDateTime blockStart, LocalDateTime blockEnd, Appointment appointment) {

        LocalDateTime appointmentStart = appointment.getDate();
        LocalDateTime appointmentEnd = appointmentStart.plusMinutes(appointment.getDurationMinutes());

        return blockStart.isBefore(appointmentEnd) && blockEnd.isAfter(appointmentStart);
    }

    private void validateCurrentWeekLimit(LocalDateTime desiredDate, boolean isAppointmentWeeklyLocked) {
        if (!isAppointmentWeeklyLocked) return;

        LocalDate today = LocalDate.now();
        LocalDate appointmentDate = desiredDate.toLocalDate();

        LocalDate lastDayAllowed = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        if (appointmentDate.isAfter(lastDayAllowed)) {
            throw new ScheduleNotAvailableException(
                    "The calendar is only open for this week (through Sunday, the " + lastDayAllowed + ")."
            );
        }
    }
}