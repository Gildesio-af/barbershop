package com.barbeshop.api.controller;

import com.barbeshop.api.dto.user.UserRequestDTO;
import com.barbeshop.api.dto.user.UserResponseDTO;
import com.barbeshop.api.security.CustomUserDetails;
import com.barbeshop.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(@AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) return ResponseEntity.status(401).build();

        String id = user.getId();
        UserResponseDTO currentUser = userService.getUserById(id);
        return ResponseEntity.ok(currentUser);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO newUser) {
        UserResponseDTO createdUser = userService.createUser(newUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.id())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    @PatchMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO updatedUser, @AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) return ResponseEntity.status(401).build();

        String id = user.getId();
        UserResponseDTO userUpdated = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) return ResponseEntity.status(401).build();

        userService.deleteUser(user.getId());
        return ResponseEntity.noContent().build();
    }
}
