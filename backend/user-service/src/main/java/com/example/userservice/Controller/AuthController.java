package com.example.userservice.Controller;

import com.example.userservice.DTO.*;
import com.example.userservice.Entity.User;
import com.example.userservice.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth") // Має співпадати з .requestMatchers("/api/auth/**") у SecurityConfig
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            return ResponseEntity.ok(authService.registerUser(userRegistrationDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String code) {
        if (authService.verifyUser(code)) {
            return ResponseEntity.ok("Account verified successfully");
        }
        return ResponseEntity.badRequest().body("Invalid or expired verification code");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
            String token = authService.loginAndGetToken(request);
            Map<String, String> response = Collections.singletonMap("token", token);
            return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Object>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.processForgotPassword(request.getEmail());

        // Повертаємо універсальну відповідь без даних (null)
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Якщо акаунт існує, інструкції надіслано.",
                null
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Object>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.processResetPassword(request.getCode(), request.getNewPassword());

        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Пароль успішно змінено.",
                null
        ));
    }
}