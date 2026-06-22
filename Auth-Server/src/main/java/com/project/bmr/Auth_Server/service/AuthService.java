package com.project.bmr.Auth_Server.service;


import org.springframework.stereotype.Service;

import com.project.bmr.Auth_Server.client.UserServiceClient;
import com.project.bmr.Auth_Server.dto.OtpRequest;
import com.project.bmr.Auth_Server.dto.UserResponse;
import com.project.bmr.Auth_Server.dto.VerifyOtpRequest;
import com.project.bmr.Auth_Server.dto.VerifyOtpResponse;
import com.project.bmr.Auth_Server.exception.InvalidOtpException;
import com.project.bmr.Auth_Server.factory.OtpSenderFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final OtpService otpService;
    private final JwtService jwtService;
    private final UserServiceClient userServiceClient;
    private final OtpSenderFactory otpSenderFactory;

    public void requestOtp(OtpRequest request) {

        String otp = otpService.generateOtp();

        otpService.saveOtp(
                request.getDestination(),
                otp
        );

        otpSenderFactory
                .getSender(request.getOtpType().name())
                .sendOtp(
                        request.getDestination(),
                        otp
                );
    }

    public VerifyOtpResponse verifyOtp(
            VerifyOtpRequest request) {

        boolean valid =
                otpService.verifyOtp(
                        request.getDestination(),
                        request.getOtp()
                );

        if (!valid) {

            throw new InvalidOtpException(
                    "Invalid or Expired OTP"
            );
        }

        try {

            UserResponse user;

            if ("EMAIL".equalsIgnoreCase(
                    request.getOtpType().name())) {

                user =
                        userServiceClient
                                .getUserByEmail(
                                        request.getDestination()
                                );

            } else {

                user =
                        userServiceClient
                                .getUserByMobile(
                                        request.getDestination()
                                );
            }

            String token =
                    jwtService.generateToken(
                            user.getEmail(),
                            user.getRole()
                    );

            return VerifyOtpResponse.builder()
                    .existingUser(true)
                    .token(token)
                    .email(user.getEmail())
                    .message("Login Successful")
                    .build();

        } catch (Exception ex) {

            return VerifyOtpResponse.builder()
                    .existingUser(false)
                    .email(request.getDestination())
                    .message("Profile not found")
                    .build();
        }
    }
}