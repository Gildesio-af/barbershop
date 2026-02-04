package com.barbeshop.api.service;

import com.barbeshop.api.dto.user.UserRequestDTO;
import com.barbeshop.api.dto.user.UserResponseDTO;
import com.barbeshop.api.exception.EntityNotFoundException;
import com.barbeshop.api.model.User;
import com.barbeshop.api.repository.UserRepository;
import com.barbeshop.api.utils.UserConverter;
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
        User savedUser = userRepository.save(user);

        return UserConverter.modelToResponse(savedUser);
    }

    public UserResponseDTO updateUser(String id, UserRequestDTO updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        UserConverter.updateModelFromRequestDTO(user, updatedUser);
        if (updatedUser.password() != null)
            user.setPassword(encoder.encode(updatedUser.password()));

        User savedUser = userRepository.save(user);

        return UserConverter.modelToResponse(savedUser);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        userRepository.delete(user);
    }
}
