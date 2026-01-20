package com.example.userservice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {
    @NotBlank(message = "Код обов'язковий")
    private String code;

    @NotBlank(message = "Новий пароль обов'язковий")
    @Size(min = 8, message = "Мінімум 8 символів")
    private String newPassword;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
