package com.project.bmr.User_Service.service;


import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.project.bmr.User_Service.dto.CreateProfileRequest;
import com.project.bmr.User_Service.dto.UserResponse;
import com.project.bmr.User_Service.entity.User;
import com.project.bmr.User_Service.exception.UserNotFoundException;
import com.project.bmr.User_Service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createProfile(
            CreateProfileRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        if (userRepository.existsByMobile(request.getMobile())) {

            throw new RuntimeException(
                    "Mobile already exists"
            );
        }

        User user = User.builder()
                .email(request.getEmail())
                //.name(request.getName())
                .mobile(request.getMobile())
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    public UserResponse getUserByEmail(
            String email) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"));

        return mapToResponse(user);
    }

    public UserResponse getUserByMobile(
            String mobile) {

        User user =
                userRepository.findByMobile(mobile)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"));

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(
            User user) {

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .mobile(user.getMobile())
                //.name(user.getName())
                .role(user.getRole())
                .build();
    }
}