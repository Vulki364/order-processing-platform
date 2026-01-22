package com.example.authservice.security;

import com.example.authservice.repository.UserRepository;
import com.example.authservice.model.Role;
import com.example.authservice.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
    public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    public OAuth2LoginSuccessHandler(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User authUser= (OAuth2User) authentication.getPrincipal();
        String email = authUser.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        new User(email, "", Role.USER)
                ));

        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }


}
