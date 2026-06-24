package com.project.bmr.Data_Service.service;


import org.springframework.web.multipart.MultipartFile;

import com.project.bmr.Data_Service.dto.UploadResponse;


public interface UploadService {

    UploadResponse upload(
            MultipartFile file,
            String role
    );
}