package com.example.userservice.Config;

import com.example.userservice.DTO.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException; // Додав про всяк випадок
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. ВАЛІДАЦІЯ DTO (@Valid) -> 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        List<ValidationErrorResponse.ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ValidationErrorResponse.ValidationError(err.getField(), err.getDefaultMessage()))
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Помилка валідації",
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 2. ПОМИЛКИ АВТОРИЗАЦІЇ (Логін, Пароль, Заблокований юзер) -> 401 Unauthorized
    // Я прибрав звідси RuntimeException!
    @ExceptionHandler({BadCredentialsException.class, DisabledException.class})
    public ResponseEntity<ValidationErrorResponse> handleAuthErrors(Exception ex) {

        List<ValidationErrorResponse.ValidationError> errors = List.of(
                new ValidationErrorResponse.ValidationError("auth", ex.getMessage())
        );

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Помилка авторизації",
                errors
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // 3. БІЗНЕС-ПОМИЛКИ (Юзер не знайдений, Email зайнятий і т.д.) -> 400 Bad Request
    // Цей метод ловить всі RuntimeException, які не є BadCredentialsException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ValidationErrorResponse> handleRuntimeExceptions(RuntimeException ex) {

        List<ValidationErrorResponse.ValidationError> errors = List.of(
                new ValidationErrorResponse.ValidationError("error", ex.getMessage())
        );

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Помилка операції",
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}