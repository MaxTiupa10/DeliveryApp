package com.example.restaurantservice.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Основна Інформація ---
    @Column(nullable = false)
    private String name;

    @Column(length = 500) // Опис може бути довгим
    private String description;

    // URL на картинку (зберігати байти в БД - це погана практика, зберігаємо лінк)
    private String imageUrl;

    // --- Контакти ---
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    // --- Бізнес-логіка ---
    @Column(nullable = false)
    private boolean isOpen; // Адмін може закрити заклад вручну

    // Зв'язок з User Service.
    // Ми не мапимо сюди об'єкт User, бо це інший мікросервіс!
    @Column(nullable = false)
    private Long ownerId;

    // --- Зв'язки ---
    // mappedBy = "restaurant" вказує на поле в класі MenuItem
    // CascadeType.ALL = видалив ресторан -> видалились страви
    // orphanRemoval = true = видалив страву зі списку -> вона зникла з БД
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();

    // --- Аудит (Senior Style) ---
    // Автоматично ставлять час створення та оновлення запису
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}