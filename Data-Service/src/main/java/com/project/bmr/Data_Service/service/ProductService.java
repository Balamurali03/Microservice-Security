package com.project.bmr.Data_Service.service;


import java.util.List;

import com.project.bmr.Data_Service.dto.ProductRequest;
import com.project.bmr.Data_Service.dto.ProductResponse;


public interface ProductService {

    ProductResponse create(
            ProductRequest request,
            String role
    );

    ProductResponse update(
            Long id,
            ProductRequest request,
            String role
    );

    void delete(
            Long id,
            String role
    );

    ProductResponse getById(
            Long id
    );

    List<ProductResponse> getAll();
}