package com.project.bmr.Auth_Server.service;


import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.bmr.Auth_Server.constants.RedisKeys;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final RedisTemplate<String,String>
            redisTemplate;

    private final PasswordEncoder passwordEncoder;

    public String generateOtp() {

        return String.format(
                "%06d",
                new Random().nextInt(999999)
        );
    }

    public void saveOtp(
            String email,
            String otp) {

        String key =
                RedisKeys.OTP_PREFIX + email;

        String encryptedOtp =
                passwordEncoder.encode(otp);

        redisTemplate.opsForValue()
                .set(
                        key,
                        encryptedOtp,
                        Duration.ofMinutes(1)
                );
    }

    public boolean verifyOtp(
            String email,
            String otp) {

        String key =
                RedisKeys.OTP_PREFIX + email;

        String storedOtp =
                redisTemplate.opsForValue()
                        .get(key);

        if(storedOtp == null) {

            return false;
        }

        boolean valid =
                passwordEncoder.matches(
                        otp,
                        storedOtp
                );

        if(valid) {

            redisTemplate.delete(key);
        }

        return valid;
    }
}