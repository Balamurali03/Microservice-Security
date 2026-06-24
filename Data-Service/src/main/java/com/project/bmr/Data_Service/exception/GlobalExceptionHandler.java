package com.project.bmr.Data_Service.exception;


import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class
    )
    public ResponseEntity<?> handleNotFound(
            ResourceNotFoundException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        Map.of(
                                "success", false,
                                "message",
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(
            UnauthorizedException.class
    )
    public ResponseEntity<?> handleUnauthorized(
            UnauthorizedException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        Map.of(
                                "success", false,
                                "message",
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(
            Exception ex
    ) {

        return ResponseEntity
                .internalServerError()
                .body(
                        Map.of(
                                "success", false,
                                "message",
                                ex.getMessage()
                        )
                );
    }
}