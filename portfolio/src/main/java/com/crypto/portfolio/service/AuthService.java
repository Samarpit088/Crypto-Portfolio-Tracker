package com.crypto.portfolio.service;

import com.crypto.portfolio.dto.AuthResponse;
import com.crypto.portfolio.dto.LoginRequest;
import com.crypto.portfolio.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest register);
    AuthResponse login(LoginRequest request);
}
