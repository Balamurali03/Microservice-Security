package com.project.bmr.Auth_Server.dto;


import com.project.bmr.Auth_Server.enums.OtpType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyOtpRequest {

    private OtpType otpType;

    private String destination;

    private String otp;
}