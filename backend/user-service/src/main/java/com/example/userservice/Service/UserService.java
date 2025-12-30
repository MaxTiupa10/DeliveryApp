package com.example.userservice.Service;


import com.example.userservice.Entity.User;
import com.example.userservice.Repisitory.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    public UserService(UserRepository userRepository,EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Пошта вже зайнята");
        }
        String code = UUID.randomUUID().toString();
        user.setVerificationCode(code);
        user.setEnabled(false);

        User savedUser = userRepository.save(user);

        emailService.sendVerificationEmail(user.getEmail(), code);

        return savedUser;
    }


    public boolean verifyUser(String verificationCode) {
        // 1. Шукаємо юзера по коду
        User user = userRepository.findByVerificationCode(verificationCode)
                .orElse(null);

        // 2. Якщо юзера немає або він вже активований — повертаємо false
        if (user == null || user.isEnabled()) {
            return false;
        }

        // 3. Активуємо юзера
        user.setEnabled(true);
        user.setVerificationCode(null); // Очищаємо код, щоб його не можна було використати вдруге
        userRepository.save(user); // Зберігаємо зміни в базу

        return true;
    }
}
