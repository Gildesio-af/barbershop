package com.barbeshop.api.model;

import com.barbeshop.api.model.enums.OperatingMode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "business_hours")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class BusinessHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;
    @Column(nullable = false)
    private LocalTime openTime;
    @Column(nullable = false)
    private LocalTime closeTime;
    @Column(name = "break_start")
    private LocalTime breakStartTime;
    @Column(name = "break_end")
    private LocalTime breakEndTime;
    private Boolean isOpen;
    @Column(name = "mode")
    @Enumerated(EnumType.STRING)
    private OperatingMode operatingMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settings_id", nullable = false)
    private StoreSetting storeSetting;
}
