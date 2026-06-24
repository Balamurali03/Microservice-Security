package com.project.bmr.Data_Service.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.bmr.Data_Service.dto.ProductRequest;
import com.project.bmr.Data_Service.dto.ProductResponse;
import com.project.bmr.Data_Service.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse>
    create(

            @RequestHeader(
                    "X-USER-ROLE"
            ) String role,

            @RequestBody
            ProductRequest request
    ) {

        return ResponseEntity.ok(
                productService.create(
                        request,
                        role
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse>
    update(

            @PathVariable Long id,

            @RequestHeader(
                    "X-USER-ROLE"
            ) String role,

            @RequestBody
            ProductRequest request
    ) {

        return ResponseEntity.ok(
                productService.update(
                        id,
                        request,
                        role
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    delete(

            @PathVariable Long id,

            @RequestHeader(
                    "X-USER-ROLE"
            ) String role
    ) {

        productService.delete(
                id,
                role
        );

        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse>
    getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                productService.getById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>>
    getAll() {

        return ResponseEntity.ok(
                productService.getAll()
        );
    }
}