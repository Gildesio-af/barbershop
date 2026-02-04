package com.barbeshop.api.controller;

import com.barbeshop.api.dto.security.JwtResponse;
import com.barbeshop.api.dto.security.LoginRequest;
import com.barbeshop.api.dto.user.AuthService;
import com.barbeshop.api.security.Auth0JwtTokenProvider;
import com.barbeshop.api.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final Auth0JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(JwtResponse.builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .role(userDetails.getAuthorities().stream().findFirst().orElseThrow().getAuthority())
                .email(userDetails.getEmail())
                .build());
    }

    @PostMapping("/recover-password")
    public ResponseEntity<String> recoverPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        authService.recoverPassword(email);
        return ResponseEntity.ok("Password recovery email sent if the email exists in our system.");
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody Map<String, String> request) {
        String newPassword = request.get("newPassword");
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password has been reset successfully.");
    }
}
