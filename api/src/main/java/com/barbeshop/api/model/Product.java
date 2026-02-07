package com.barbeshop.api.model;

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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;
    @Column(name = "p_name", nullable = false, length = 50, unique = true)
    private String name;
    @Builder.Default
    private Integer quantity = 0;
    @Builder.Default
    private Integer minStock = 5;
    @Column(nullable = false)
    private Double price;
    private String imageUrl;
    private boolean isVisible = true;
    @Column(name = "is_active")
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
