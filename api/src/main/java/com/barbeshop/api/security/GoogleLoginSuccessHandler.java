package com.barbeshop.api.security;

import com.barbeshop.api.model.Role;
import com.barbeshop.api.model.User;
import com.barbeshop.api.model.enums.AuthProvider;
import com.barbeshop.api.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Auth0JwtTokenProvider tokenService;
    private final UserRepository userRepository;
    //TODO: ajustar para pegar do front-end correto
    //    @Value("${app.frontend.url:http://localhost:3000}") // URL do seu React
    //    private String frontendUrl;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        String email = oauthToken.getPrincipal().getAttribute("email");
        String name = oauthToken.getPrincipal().getAttribute("given_name");

        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(name);
            newUser.setAuthProvider(AuthProvider.GOOGLE);
            newUser.setPassword("");
            newUser.setLoyaltyPoints(0);
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setRole(new Role(2, "USER"));

            return userRepository.save(newUser);
        });

        CustomUserDetails userDetails = CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .authorities(user.getRole().getRole())
                .build();

        String token = tokenService.generateToken(userDetails);
        System.out.println("dados do google: " + attributes);

        String frontendUrl = "http://localhost:8080/test/show-token";
        String targetUrl = frontendUrl + "/oauth2/redirect?token=" + token;

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
