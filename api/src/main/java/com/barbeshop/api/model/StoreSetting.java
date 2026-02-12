package com.barbeshop.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_settings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class StoreSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    @Column(name = "store_name", nullable = false, length = 100)
    private String name;
    @Column(length = 150)
    private String address;
    @Column(length = 20)
    private String phone;
    @Column(name = "instagram_handle", length =50)
    private String instagram;
    @Column(name = "show_products_publicly")
    private Boolean productsEnabled;
}
