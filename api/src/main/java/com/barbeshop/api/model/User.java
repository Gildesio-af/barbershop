package com.barbeshop.api.model;

import com.barbeshop.api.model.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "barbershop_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;
    @Column(nullable = false, unique = true, length = 100)
    private String username;
    @Column(name = "user_password")
    private String password;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(length = 20)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;
    private Integer loyaltyPoints;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
