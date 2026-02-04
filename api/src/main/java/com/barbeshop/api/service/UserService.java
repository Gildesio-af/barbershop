package com.barbeshop.api.service;

import com.barbeshop.api.dto.user.PasswordUpdateDTO;
import com.barbeshop.api.dto.user.UserRequestDTO;
import com.barbeshop.api.dto.user.UserResponseDTO;
import com.barbeshop.api.dto.user.UserUpdateDTO;
import com.barbeshop.api.shared.exception.EntityNotFoundException;
import com.barbeshop.api.model.User;
import com.barbeshop.api.repository.UserRepository;
import com.barbeshop.api.shared.utils.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        return UserConverter.modelToResponse(user);
    }

    public UserResponseDTO createUser(UserRequestDTO newUser) {
        User user = UserConverter.requestDTOToModel(newUser, encoder.encode(newUser.password()));
        replacePhoneNumber(user);
        User savedUser = userRepository.save(user);

        return UserConverter.modelToResponse(savedUser);
    }

    public UserResponseDTO updateUser(String id, UserUpdateDTO updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        UserConverter.updateModelFromRequestDTO(user, updatedUser);
        replacePhoneNumber(user);
        User savedUser = userRepository.save(user);

        return UserConverter.modelToResponse(savedUser);
    }

    public UserResponseDTO updateUserPassword(String id, PasswordUpdateDTO passwordUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        if (!encoder.matches(passwordUpdate.currentPassword(), user.getPassword()))
            throw new IllegalArgumentException("Current password is incorrect");

        user.setPassword(encoder.encode(passwordUpdate.newPassword()));
        User savedUser = userRepository.save(user);

        return UserConverter.modelToResponse(savedUser);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        userRepository.delete(user);
    }

    private void replacePhoneNumber(User user) {
        String phoneNumber = user.getPhoneNumber();
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            String sanitizedNumber = phoneNumber.replaceAll("\\D", "");
            user.setPhoneNumber(sanitizedNumber);
        }
    }
}
