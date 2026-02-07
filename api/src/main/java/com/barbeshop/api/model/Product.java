package com.barbeshop.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;
    @Column(name = "p_name", nullable = false, length = 50)
    private String name;
    @Builder.Default
    private Integer quantity = 0;
    @Builder.Default
    private Integer minStock = 5;
    @Column(nullable = false)
    private Double price;
    private String imageUrl;
    private boolean isVisible = true;
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
