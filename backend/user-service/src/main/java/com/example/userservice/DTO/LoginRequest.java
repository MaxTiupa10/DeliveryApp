package com.example.userservice.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email не може бути порожнім")
        @Email(message = "Некоректний формат email")
        String email,
        @NotBlank(message = "Пароль не може бути порожнім")
        @Size(min = 8, message = "Пароль має містити мінімум 8 символів")
        String password
) {}