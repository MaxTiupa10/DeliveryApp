package com.example.userservice.Service;

import com.example.userservice.DTO.LoginRequest;
import com.example.userservice.DTO.UserRegistrationDto;
import com.example.userservice.Entity.Role;
import com.example.userservice.Entity.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private static final long LINK_EXPIRATION_MINUTES = 30;
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public User registerUser(UserRegistrationDto request) {
        // .isPresent() — перевіряє, чи є щось всередині "коробки"
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Пошта вже зайнята");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        String code = UUID.randomUUID().toString();
        user.setVerificationCode(code);

        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), code);

        return savedUser;
    }

    public String loginAndGetToken(LoginRequest loginRequest) {
        // .orElseThrow() — елегантно: "Дай юзера АБО кинь помилку"
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new BadCredentialsException("Користувача не знайдено"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Невірний пароль");
        }

        if (!user.isEnabled()) {
            throw new BadCredentialsException("Акаунт не активовано");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    @Transactional
    public boolean verifyUser(String verificationCode) {
        // Тут зручніше .orElse(null), бо нам треба перевірити ще й isEnabled
        User user = userRepository.findByVerificationCode(verificationCode).orElse(null);

        if (user == null || user.isEnabled()) {
            return false;
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        userRepository.save(user);
        return true;
    }

    @Transactional
    public void processForgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            // ЛОГУЄМО спробу (для безпеки), але НЕ кидаємо помилку клієнту!
            log.warn("Спроба скидання пароля для неіснуючого email: {}", email);
            return; // Просто виходимо. Клієнт отримає 200 OK.
        }

        User user = userOptional.get();

        // Генеруємо токен
        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);

        // Встановлюємо "термін придатності" (наприклад, +30 хвилин від зараз)
        user.setResetPasswordTokenExpiry(LocalDateTime.now().plusMinutes(LINK_EXPIRATION_MINUTES));

        userRepository.save(user);

        // ВАЖЛИВО: Відправка пошти має бути асинхронною (Async),
        // щоб не тримати транзакцію БД відкритою.
        // Передаємо тільки email і token, щоб сервіс пошти сам сформував посилання.
        emailService.sendResetToken(user.getEmail(), token);
    }

    @Transactional
    public void processResetPassword(String token, String newPassword) {
        // Шукаємо юзера за токеном
        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new RuntimeException("Недійсний або використаний токен"));

        // Перевіряємо, чи не "протух" токен
        if (user.getResetPasswordTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Термін дії посилання вичерпано. Замовте нове.");
        }

        // Оновлюємо пароль
        user.setPassword(passwordEncoder.encode(newPassword));

        // "Спалюємо" токен, щоб його не можна було використати вдруге
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);

        userRepository.save(user);
    }
}