package com.project.bmr.Data_Service.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.bmr.Data_Service.dto.UploadResponse;
import com.project.bmr.Data_Service.service.UploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse>
    upload(

            @RequestHeader(
                    "X-USER-ROLE"
            ) String role,

            @RequestParam(
                    "file"
            ) MultipartFile file
    ) {

        return ResponseEntity.ok(
                uploadService.upload(
                        file,
                        role
                )
        );
    }
}
