package com.project.bmr.User_Service.dto;

import lombok.Data;

@Data
public class CreateProfileRequest {
	
	private Long id;

    private String email;

    private String mobile;

    private String name;

    private String role;

}
