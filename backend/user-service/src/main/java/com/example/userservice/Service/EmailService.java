package com.example.userservice.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendVerificationEmail(String toEmail, String code) {
        try {
            String subject = "Підтвердження реєстрації";
            String confirmationUrl = "http://localhost:8081/api/auth/verify?code=" + code;
            String messageText = "Дякуємо за реєстрацію!\n\n" +
                    "Будь ласка, натисніть на посилання нижче, щоб активувати акаунт:\n" +
                    confirmationUrl;

            sendEmail(toEmail, subject, messageText);
        } catch (Exception e) {
            System.err.println("Помилка відправки верифікації: " + e.getMessage());
        }
    }

    // --- НОВИЙ МЕТОД ---
    @Async
    public void sendResetToken(String toEmail, String token) {
        try {
            String subject = "Відновлення пароля";

            // Якщо у тебе є фронтенд, посилання буде вести на нього.
            // Для Postman/API ми просто надсилаємо токен.
            String messageText = "Ви (або хтось інший) подали запит на зміну пароля.\n\n" +
                    "Ваш код для відновлення: " + token + "\n\n" +
                    "Скопіюйте цей код і використайте його в додатку для створення нового пароля.\n" +
                    "Цей код дійсний протягом 30 хвилин.";

            sendEmail(toEmail, subject, messageText);
        } catch (Exception e) {
            System.err.println("Помилка відправки токена відновлення: " + e.getMessage());
        }
    }

    // Допоміжний приватний метод, щоб не дублювати код (DRY)
    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
        System.out.println("Лист успішно відправлено на: " + to);
    }
}