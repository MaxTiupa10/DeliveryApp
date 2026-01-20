package com.example.userservice.Controller;


import com.example.userservice.DTO.ApiResponse;
import com.example.userservice.DTO.UserProfileResponse;
import com.example.userservice.Entity.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUser(@AuthenticationPrincipal User userDetails) {

        UserProfileResponse profile = userService.getUserProfile(userDetails.getUsername());

        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Профіль отримано",
                profile
        ));
    }



}
