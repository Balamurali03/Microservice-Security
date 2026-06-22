package com.project.bmr.Auth_Server.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyOtpResponse {

    private boolean existingUser;

    private String token;

    private String email;

    private String message;
}
