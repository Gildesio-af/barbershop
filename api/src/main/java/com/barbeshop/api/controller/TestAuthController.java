package com.barbeshop.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestAuthController {

    @GetMapping("/show-token")
    public Map<String, String> showToken(@RequestParam("token") String token) {
        // Isso simula a página do React recebendo o token
        return Map.of(
                "message", "Login com Google funcionou!",
                "seu_jwt", token
        );
    }
}
