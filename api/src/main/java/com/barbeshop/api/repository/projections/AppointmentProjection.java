package com.barbeshop.api.repository.projections;

import com.barbeshop.api.model.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentProjection {
    String getId();
    String getUserId();
    String getCustomerName();
    Double getTotalPrice();
    LocalDateTime getAppointmentDate();
    LocalDateTime getCreatedAt();
    Integer getDurationMinutes();
    AppointmentStatus getStatus();
    String getUserName();
    List<ServiceProjection> getServices();
    List<ProductProjection> getProducts();

}
