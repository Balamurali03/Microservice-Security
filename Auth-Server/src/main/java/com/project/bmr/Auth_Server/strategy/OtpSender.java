package com.project.bmr.Auth_Server.strategy;

public interface OtpSender {
	void sendOtp(
            String destination,
            String otp
    );
}
