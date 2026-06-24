package com.project.bmr.User_Service.controller;



import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bmr.User_Service.dto.AdminResponse;
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
    
    @GetMapping("/admins")
    public List<AdminResponse> getAdmins() {

        return userService.getAdmins();
    }
}