package com.project.bmr.Data_Service.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponse {

    private Long id;

    private String email;

    private String name;
}