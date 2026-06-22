package com.project.bmr.Auth_Server.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.bmr.Auth_Server.dto.UserResponse;

@FeignClient(
        name = "USER-SERVICE"
)
public interface UserServiceClient {

    @GetMapping("/users/email/{email}")
    UserResponse getUserByEmail(
            @PathVariable("email") String email
    );
    
    @GetMapping("/users/mobile/{mobile}")
    UserResponse getUserByMobile(
            @PathVariable String mobile
    );
}