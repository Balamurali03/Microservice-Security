package com.project.bmr.Data_Service.client;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.bmr.Data_Service.dto.AdminResponse;


@FeignClient(
        name = "USER-SERVICE"
)
public interface UserServiceClient {

    @GetMapping("/users/admins")
    List<AdminResponse> getAllAdmins();
}