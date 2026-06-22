package com.project.bmr.Auth_Server.factory;


import org.springframework.stereotype.Component;

import com.project.bmr.Auth_Server.strategy.OtpSender;

import java.util.Map;

@Component
public class OtpSenderFactory {

    private final Map<String, OtpSender> senders;

    public OtpSenderFactory(
            Map<String, OtpSender> senders) {

        this.senders = senders;
    }

    public OtpSender getSender(
            String type) {

        OtpSender sender =
                senders.get(type);

        if(sender == null) {

            throw new RuntimeException(
                    "Invalid OTP Type"
            );
        }

        return sender;
    }
}
