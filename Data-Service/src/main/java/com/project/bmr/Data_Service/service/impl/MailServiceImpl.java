package com.project.bmr.Data_Service.service.impl;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.project.bmr.Data_Service.service.MailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl
        implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendMail(
            String to,
            String subject,
            String body
    ) {

        SimpleMailMessage mail =
                new SimpleMailMessage();

        mail.setTo(to);

        mail.setSubject(subject);

        mail.setText(body);

        mailSender.send(mail);
    }
}