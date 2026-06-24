package com.project.bmr.User_Service.dto;

import lombok.Data;

@Data
public class CreateProfileRequest {
	
	private Long id;

    private String email;

    private String mobile;

    private String role;
    
    private String name;

}
