package com.project.bmr.Data_Service.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResponse {

    private boolean success;

    private String message;

    private Long totalRecords;
}