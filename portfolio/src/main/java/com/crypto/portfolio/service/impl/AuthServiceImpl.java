package com.crypto.portfolio.service.impl;

import com.crypto.portfolio.dto.AuthResponse;
import com.crypto.portfolio.dto.LoginRequest;
import com.crypto.portfolio.dto.RegisterRequest;
import com.crypto.portfolio.entity.Role;
import com.crypto.portfolio.entity.User;
import com.crypto.portfolio.repository.RoleRepository;
import com.crypto.portfolio.repository.UserRepository;
import com.crypto.portfolio.security.JWTService;
import com.crypto.portfolio.service.AuthService;
import com.crypto.portfolio.service.EmailService;
import com.crypto.portfolio.service.EventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final EventPublisherService eventPublisherService;
    private final EmailService emailService;

    @Override
    public AuthResponse register(RegisterRequest request){
        log.info("Register request received from email: {} ",request.getEmail());
        if(userRepository.existsByEmail(request.getEmail())){
            return AuthResponse.builder()
                    .token(null)
                    .message("Email already exists")
                    .build();
        }
        Role userRole = roleRepository.findByName("USER").orElseThrow(()->new RuntimeException("Default role USER not found"));
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);
        eventPublisherService.publishUserRegisteredEvents(user.getEmail());
        emailService.sendWelcomeEmail(user.getEmail(),user.getName());

        log.info("User registered successfully: {} ", user.getEmail());

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .message("Email registered successfully")
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request){
        log.info("Login request received from email: {}",request.getEmail());
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("Invalid email or password"));
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())) throw new RuntimeException("Invalid email or password");
        String token = jwtService.generateToken(user.getEmail());
        log.info("Login successful for email: {}",user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }
}
