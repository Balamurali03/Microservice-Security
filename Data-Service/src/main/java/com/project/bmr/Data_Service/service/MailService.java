package com.project.bmr.Data_Service.service;


public interface MailService {

    void sendMail(
            String to,
            String subject,
            String body
    );
}
