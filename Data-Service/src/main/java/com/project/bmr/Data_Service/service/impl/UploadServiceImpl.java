package com.project.bmr.Data_Service.service.impl;


import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.bmr.Data_Service.batch.ExcelProduct;
import com.project.bmr.Data_Service.batch.ExcelProductItemReader;
import com.project.bmr.Data_Service.batch.ExcelReader;
import com.project.bmr.Data_Service.dto.UploadResponse;
import com.project.bmr.Data_Service.exception.UnauthorizedException;
import com.project.bmr.Data_Service.service.UploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl
        implements UploadService {

    private final ExcelReader excelReader;

    private final ExcelProductItemReader
            itemReader;

    private final JobLauncher jobLauncher;

    private final Job importProductJob;

    @Override
    public UploadResponse upload(
            MultipartFile file,
            String role
    ) {

        if (!"ADMIN".equalsIgnoreCase(role)) {

            throw new UnauthorizedException(
                    "Only Admin Can Upload"
            );
        }

        try {

            List<ExcelProduct> products =
                    excelReader.read(
                            file.getInputStream()
                    );

            itemReader.setProducts(
                    products
            );

            JobParameters params =
                    new JobParametersBuilder()
                            .addLong(
                                    "time",
                                    System.currentTimeMillis()
                            )
                            .toJobParameters();

            jobLauncher.run(
                    importProductJob,
                    params
            );

            return UploadResponse.builder()
                    .success(true)
                    .message(
                            "File Uploaded Successfully"
                    )
                    .totalRecords(
                            (long) products.size()
                    )
                    .build();

        } catch (Exception ex) {

            throw new RuntimeException(
                    ex.getMessage()
            );
        }
    }
}