package com.project.bmr.Auth_Server.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.bmr.Auth_Server.dto.OtpRequest;
import com.project.bmr.Auth_Server.dto.VerifyOtpRequest;
import com.project.bmr.Auth_Server.dto.VerifyOtpResponse;
import com.project.bmr.Auth_Server.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(
            @Valid @RequestBody OtpRequest request) {

        authService.requestOtp(request);

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "OTP Sent Successfully",
                        "otpType", request.getOtpType().name(),
                        "destination", request.getDestination()
                )
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<VerifyOtpResponse> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        return ResponseEntity.ok(
                authService.verifyOtp(request)
        );
    }
}