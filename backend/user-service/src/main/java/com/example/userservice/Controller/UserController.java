package com.example.userservice.Controller;


import com.example.userservice.Entity.User;
import com.example.userservice.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Користувач створений! Перевірте пошту.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("code") String code) {
        boolean isVerified = userService.verifyUser(code);

        if (isVerified) {
            return ResponseEntity.ok("Вітаємо! Ваш акаунт успішно активовано. Тепер ви можете увійти.");
        } else {
            return ResponseEntity.badRequest().body("Помилка активації: невірний код або акаунт вже активний.");
        }
    }
    @PostMapping("/hello")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello");
    }


}
