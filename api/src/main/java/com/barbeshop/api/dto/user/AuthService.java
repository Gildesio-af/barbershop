package com.barbeshop.api.dto.user;

import com.barbeshop.api.dto.security.EmailRequestDTO;
import com.barbeshop.api.dto.security.PasswordRequestDTO;
import com.barbeshop.api.model.PasswordResetToken;
import com.barbeshop.api.model.User;
import com.barbeshop.api.repository.PasswordTokenRepository;
import com.barbeshop.api.repository.UserRepository;
import com.barbeshop.api.service.EmailService;
import com.barbeshop.api.shared.exception.EntityNotFoundException;
import com.barbeshop.api.shared.exception.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordTokenRepository passwordRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder encoder;

    public void recoverPassword(EmailRequestDTO emailRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(emailRequest.email());
        if (optionalUser.isEmpty()) return;

        User user = optionalUser.get();

        PasswordResetToken token = passwordRepository.findByUserId(user.getId())
                .orElse(new PasswordResetToken(user, UUID.randomUUID().toString()));

        passwordRepository.save(token);

        String link = "http://localhost:8080/auth/reset-password?token=" + token.getToken();

        emailService.sendRecoveryEmail(emailRequest.email(), user.getUsername(), link);
    }

    public void resetPassword(String token, PasswordRequestDTO newPasswordRequest) {
        PasswordResetToken resetToken = passwordRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Password Reset Token", token));

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            passwordRepository.delete(resetToken);
            throw new TokenExpiredException("Password reset token has expired");
        }
        User user = userRepository.findById(resetToken.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User", resetToken.getUser().getId()));

        user.setPassword(encoder.encode(newPasswordRequest.password()));
        userRepository.save(user);

        passwordRepository.delete(resetToken);
    }
}
