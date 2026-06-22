package com.project.bmr.Auth_Server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.bmr.Auth_Server.strategy.OtpSender;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component("MOBILE")
@RequiredArgsConstructor
@Slf4j
public class SmsService implements OtpSender {

    
    @Value("${twilio.phone-number}")
    private String phoneNumber;

     @Override
    public void sendOtp(
            String mobile,
            String otp) {

        Message sms = Message.creator(
                new PhoneNumber(mobile),
                new PhoneNumber(phoneNumber),
                "Your OTP is : " + otp
        ).create();

        log.info("SMS Sent Successfully. SID : {}", sms.getSid());

        //return sms.getSid();
    }
}
