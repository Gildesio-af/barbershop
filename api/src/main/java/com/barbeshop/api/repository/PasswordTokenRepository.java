package com.barbeshop.api.repository;

import com.barbeshop.api.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUserId(String id);
}
