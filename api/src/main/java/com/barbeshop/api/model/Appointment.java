package com.barbeshop.api.model;

import com.barbeshop.api.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;
    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime date;
    @Column(name = "total_amount")
    private Double price;
    @Column(name = "total_duration")
    private Integer durationMinutes;
    @Column(name = "app_status", columnDefinition = "DEFAULT 'PENDING'")
    private AppointmentStatus status;
    @Column(columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User customer;
    // virus

}
