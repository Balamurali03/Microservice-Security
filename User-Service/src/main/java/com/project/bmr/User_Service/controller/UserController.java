package com.project.bmr.User_Service.controller;



import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.bmr.User_Service.dto.CreateProfileRequest;
import com.project.bmr.User_Service.dto.UserResponse;
import com.project.bmr.User_Service.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/profile/create")
    public ResponseEntity<?> createProfile(
            @Valid @RequestBody
            CreateProfileRequest request) {

        userService.createProfile(request);

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message",
                        "Profile Created Successfully"
                )
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse>
    getByEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(
                userService.getUserByEmail(email)
        );
    }

    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<UserResponse>
    getByMobile(
            @PathVariable String mobile) {

        return ResponseEntity.ok(
                userService.getUserByMobile(mobile)
        );
    }
}