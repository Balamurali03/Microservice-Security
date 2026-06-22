package com.project.bmr.Auth_Server.exception;


public class InvalidOtpException extends RuntimeException {

    public InvalidOtpException(String message) {
        super(message);
    }
}