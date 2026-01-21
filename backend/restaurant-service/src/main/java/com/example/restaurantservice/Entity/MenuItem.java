package com.example.restaurantservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String imageUrl;

    // BigDecimal обов'язковий для грошей (double дає похибки при округленні)
    @Column(nullable = false)
    private BigDecimal price;

    private boolean available; // Чи є страва в наявності (стоп-лист)

    // Зв'язок "Багато до одного"
    // FetchType.LAZY - завантажувати дані про ресторан тільки коли ми явно просимо (економія пам'яті)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore // Щоб не було циклічного JSON (Ресторан -> Меню -> Ресторан...)
    private Restaurant restaurant;
}