package com.barbeshop.api.shared.utils;

import com.barbeshop.api.dto.user.UserRequestDTO;
import com.barbeshop.api.dto.user.UserResponseDTO;
import com.barbeshop.api.dto.user.UserUpdateDTO;
import com.barbeshop.api.model.Role;
import com.barbeshop.api.model.User;
import com.barbeshop.api.model.enums.AuthProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserConverter {

    public static UserResponseDTO modelToResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .loyaltyPoints(user.getLoyaltyPoints())
                .roleName(user.getRole().getRole())
                .createdAt(user.getCreatedAt().toString())
                .build();
    }

    public static User requestDTOToModel(UserRequestDTO user, String passwordHash) {
        return User.builder()
                .username(user.username())
                .password(passwordHash)
                .email(user.email())
                .phoneNumber(user.phoneNumber())
                .authProvider(AuthProvider.EMAIL)
                .loyaltyPoints(0)
                .createdAt(LocalDateTime.now())
                .role(new Role(2,"USER"))
                .build();
    }

    public static void updateModelFromRequestDTO(User user, UserUpdateDTO userDTO) {
        if (userDTO.username() != null) user.setUsername(userDTO.username());
        if (userDTO.email() != null) user.setEmail(userDTO.email());
        if (userDTO.phoneNumber() != null) user.setPhoneNumber(userDTO.phoneNumber());
    }
}
