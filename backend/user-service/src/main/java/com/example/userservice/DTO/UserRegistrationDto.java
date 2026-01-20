package com.example.userservice.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {

    @NotBlank(message = "Email обов'язковий")
    @Email(message = "Невірний формат email")
    private String email;

    @NotBlank(message = "Пароль обов'язковий")
    @Size(min = 8, message = "Пароль має містити мінімум 8 символів")
    private String password;

    @NotBlank(message = "Ім'я обов'язкове")
    private String firstName;

    @NotBlank(message = "Прізвище обов'язкове")
    private String lastName;

    @NotBlank(message = "Номер телефону обов'язковий")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Невірний формат телефону (наприклад: +380123456789)")
    private String phoneNumber;

    @NotBlank(message = "Місто обов'язкове")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯіІїЇєЄґҐ\\s\\-]+$",
            message = "Введіть лише назву міста (без цифр, ком та вулиць)")
    private String address;

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}