package com.barbeshop.api.repository;

import com.barbeshop.api.model.AppointmentServiceItem;
import com.barbeshop.api.repository.projections.ServiceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentServiceItemRepository extends JpaRepository<AppointmentServiceItem, Integer> {
    @Query(value = """
            SELECT s.id AS id,
                   s.s_name AS name,
                   s.image_url AS imageUrl,
                   aps.price_at_moment AS price,
                   s.s_type AS type
            FROM appointment_services aps
            JOIN service s ON aps.service_id = s.id
            WHERE aps.appointment_id = :appointmentId
    """, nativeQuery = true)
    List<ServiceProjection> findServicesByAppointmentId(String appointmentId);
}
