package com.barbeshop.api.repository;

import com.barbeshop.api.model.Appointment;
import com.barbeshop.api.repository.projections.AppointmentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    @Query(value = """
            SELECT COUNT(*)
            FROM appointment a
            WHERE a.app_status <> 'CANCELED'
              AND a.appointment_date < :endDateTime
              AND DATE_ADD(a.appointment_date, INTERVAL a.total_duration MINUTE) > :startDateTime
            """, nativeQuery = true)
    long existsOverlappingAppointment(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    @Query(value = """
            SELECT COUNT(*) > 0
            FROM appointment a
            WHERE a.app_status <> 'CANCELED'
              AND (
                  DATE_ADD(a.appointment_date, INTERVAL a.total_duration MINUTE) = :startTime
                  OR
                  DATE_ADD(a.appointment_date, INTERVAL 45 MINUTE) = :startTime
              )
            """, nativeQuery = true)
    boolean existsReferenceForStartTime(LocalDateTime startTime);

    @Query(value = """
            SELECT COUNT(*) > 0
            FROM appointment a
            WHERE a.user_id = :customerId
              AND a.app_status = 'PENDING'
            """, nativeQuery = true)
    long verifyIfCustomerHasPendingAppointments(String customerId);

    @Query(value = """
            SELECT a.id AS id,
                   a.user_id AS userId,
                   u.username AS userName,
                   a.appointment_date AS appointmentDate,
                   a.total_amount AS totalPrice,
                   a.created_at AS createdAt,
                   a.total_duration AS durationMinutes,
                   a.app_status AS status
            FROM appointment a
            JOIN barbershop_user u ON a.user_id = u.id
            WHERE a.id = :appointmentId
    """, nativeQuery = true)
    Optional<AppointmentProjection> findAppointmentById(String appointmentId);

    @Query(value = """
            SELECT a.id AS id,
                   a.user_id AS userId,
                   u.username AS userName,
                   a.appointment_date AS appointmentDate,
                   a.total_amount AS totalPrice,
                   a.created_at AS createdAt,
                   a.total_duration AS durationMinutes,
                   a.app_status AS status
            FROM appointment a
            JOIN barbershop_user u ON a.user_id = u.id
            WHERE a.appointment_date BETWEEN :startDateTime AND :endDateTime
    """, nativeQuery = true)
    Page<AppointmentProjection> findAllAppointmentsWithPagination(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);


    @EntityGraph(attributePaths = {"customer", "services", "products"})
    Optional<Appointment> findDetailedById(String id);

    @Query(value = """
            SELECT COUNT(*) 
            FROM appointment a
            WHERE a.app_status <> 'CANCELED' 
              AND a.appointment_date BETWEEN :startOfDay AND :endOfDay
            """, nativeQuery = true)
    int countAppointmentsOnDate(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT a FROM Appointment a WHERE a.date BETWEEN :start AND :end AND a.status <> com.barbeshop.api.model.enums.AppointmentStatus.CANCELED")
    List<Appointment> findActiveAppointmentsByDateRange(LocalDateTime start, LocalDateTime end);

    @Query(value = """
           UPDATE Appointment a 
              SET a.status = com.barbeshop.api.model.enums.AppointmentStatus.CANCELED 
              WHERE a.id = :appointmentId
           """)
    @Modifying
    int cancelAppointmentById(@Param("appointmentId") String appointmentId);

    Page<Appointment> findAllByCustomerId(String customerId, Pageable pageable);

    @Query(value = """
           UPDATE Appointment a 
              SET a.status = com.barbeshop.api.model.enums.AppointmentStatus.CONFIRMED 
              WHERE a.id = :appointmentId AND a.status = com.barbeshop.api.model.enums.AppointmentStatus.PENDING
           """)
    int acceptAppointmentById(String appointmentId);
}
