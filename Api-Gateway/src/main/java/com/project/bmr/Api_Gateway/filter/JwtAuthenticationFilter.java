package com.project.bmr.Api_Gateway.filter;



import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.project.bmr.Api_Gateway.security.JwtService;

import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter {

    private final JwtService jwtService;

    private static final List<String> OPEN_APIS =
            List.of(
                    "/auth/request-otp",
                    "/auth/verify-otp",
                    "/users/profile"
            );

    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path =
                exchange.getRequest()
                        .getURI()
                        .getPath();

        boolean open =
                OPEN_APIS.stream()
                        .anyMatch(path::startsWith);

        if(open) {

            return chain.filter(exchange);
        }

        String authHeader =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst("Authorization");

        if(authHeader == null) {

            throw new RuntimeException(
                    "Authorization Missing"
            );
        }

        String token =
                authHeader.substring(7);

        Claims claims =
                jwtService.validateToken(token);

        ServerHttpRequest request =
                exchange.getRequest()
                        .mutate()
                        .header(
                                "X-User-Email",
                                claims.getSubject())
                        .header(
                                "X-Role",
                                claims.get(
                                        "role",
                                        String.class))
                        .build();

        return chain.filter(
                exchange
                        .mutate()
                        .request(request)
                        .build());
    }
}