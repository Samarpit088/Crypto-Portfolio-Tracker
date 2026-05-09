package com.crypto.portfolio.controller;

import com.crypto.portfolio.dto.AuthResponse;
import com.crypto.portfolio.dto.LoginRequest;
import com.crypto.portfolio.dto.RegisterRequest;
import com.crypto.portfolio.entity.Role;
import com.crypto.portfolio.entity.User;
import com.crypto.portfolio.repository.RoleRepository;
import com.crypto.portfolio.repository.UserRepository;
import com.crypto.portfolio.security.JWTService;
import com.crypto.portfolio.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTService jwtService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    @GetMapping("/oauth-success")
    public ResponseEntity<AuthResponse> oauthSuccess(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        User user = userRepository.findByEmail(email).orElseGet(()->{
            Role userRole = roleRepository.findByName("USER").orElseThrow(()->new RuntimeException("Default role user not found"));

            User newUser = User.builder()
                    .name(name)
                    .email(email)
                    .password("")
                    .roles(Set.of(userRole))
                    .build();
            return userRepository.save(newUser);
        });

        String token = jwtService.generateToken(user.getEmail());
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .message("OAuth login successfull")
                .build();
        return ResponseEntity.ok(response);
    }
}
