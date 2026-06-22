package com.project.bmr.Auth_Server.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.project.bmr.Auth_Server.strategy.OtpSender;

@Component("EMAIL")
@RequiredArgsConstructor
@Slf4j
public class EmailService implements OtpSender {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(
            String email,
            String otp) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Your Login OTP");

        message.setText("""
                Hello,

                Your OTP is: %s

                This OTP is valid for 1 minute.

                Do not share it with anyone.

                Regards,
                BMR Team
                """.formatted(otp));

        mailSender.send(message);

        log.info("OTP email sent to {}", email);
    }
}
