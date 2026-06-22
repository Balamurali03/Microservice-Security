package com.project.bmr.Api_Gateway.Config;


import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.bmr.Api_Gateway.filter.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class AuthenticationFilterConfig {

    private final JwtAuthenticationFilter filter;

    @Bean
    GlobalFilter globalFilter() {

        return filter::filter;
    }
}
