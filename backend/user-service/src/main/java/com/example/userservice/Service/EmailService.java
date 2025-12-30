package com.example.userservice.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String fromEmail; // Ваша пошта (з налаштувань), щоб знати від кого лист

    public void sendVerificationEmail(String toEmail, String code) {
        try {
            // 1. Формуємо тему і текст листа
            String subject = "Підтвердження реєстрації";
            // Посилання, по якому клікне юзер
            String confirmationUrl = "http://localhost:8081/api/user/verify?code=" + code;
            String messageText = "Дякуємо за реєстрацію!\n\n" +
                    "Будь ласка, натисніть на посилання нижче, щоб активувати акаунт:\n" +
                    confirmationUrl;

            // 2. Створюємо об'єкт листа
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(messageText);

            // 3. Відправляємо
            mailSender.send(message);
            System.out.println("Лист успішно відправлено на: " + toEmail);

        } catch (Exception e) {
            // Логуємо помилку, якщо щось пішло не так (наприклад, немає інтернету)
            System.err.println("Помилка відправки листа: " + e.getMessage());
        }
    }
}