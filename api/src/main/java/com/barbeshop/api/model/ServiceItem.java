package com.barbeshop.api.model;

import com.barbeshop.api.model.enums.ServiceType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@SQLRestriction("is_active = false")
public class ServiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;
    @Column(name = "s_name", nullable = false, length = 50,unique = true)
    private String name;
    @Column(name = "s_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType type;
    @Column(name = "duration", nullable = false)
    private Integer durationInMinutes;
    @Column(nullable = false)
    private Double price;
    private String imageUrl;
    @Builder.Default
    private boolean isVisible = true;
    @Builder.Default
    @Column(name = "is_active")
    private boolean isDeleted = false;
}

