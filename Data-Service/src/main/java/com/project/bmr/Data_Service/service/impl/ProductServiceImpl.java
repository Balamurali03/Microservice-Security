package com.project.bmr.Data_Service.service.impl;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.bmr.Data_Service.dto.ProductRequest;
import com.project.bmr.Data_Service.dto.ProductResponse;
import com.project.bmr.Data_Service.elastic.ProductDocument;
import com.project.bmr.Data_Service.elastic.ProductElasticRepository;
import com.project.bmr.Data_Service.entity.ProductData;
import com.project.bmr.Data_Service.enums.Role;
import com.project.bmr.Data_Service.exception.ResourceNotFoundException;
import com.project.bmr.Data_Service.exception.UnauthorizedException;
import com.project.bmr.Data_Service.repository.ProductRepository;
import com.project.bmr.Data_Service.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl
        implements ProductService {

    private final ProductRepository repository;

    private final ProductElasticRepository
            elasticRepository;

    @Override
    public ProductResponse create(
            ProductRequest request,
            String role
    ) {

        validateAdmin(role);

        ProductData entity =
                ProductData.builder()
                        .productCode(
                                request.getProductCode()
                        )
                        .name(request.getName())
                        .category(
                                request.getCategory()
                        )
                        .quantity(
                                request.getQuantity()
                        )
                        .price(
                                request.getPrice()
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .updatedAt(
                                LocalDateTime.now()
                        )
                        .build();

        ProductData saved =
                repository.save(entity);

        syncElastic(saved);

        return map(saved);
    }
    
    @Override
    public ProductResponse update(
            Long id,
            ProductRequest request,
            String role
    ) {

        validateAdmin(role);

        ProductData data =
                repository.findById(id)
                        .orElseThrow(
                                () ->
                                new ResourceNotFoundException(
                                        "Product Not Found"
                                )
                        );

        data.setName(
                request.getName()
        );

        data.setCategory(
                request.getCategory()
        );

        data.setQuantity(
                request.getQuantity()
        );

        data.setPrice(
                request.getPrice()
        );

        data.setUpdatedAt(
                LocalDateTime.now()
        );

        ProductData updated =
                repository.save(data);

        syncElastic(updated);

        return map(updated);
    }

    @Override
    public void delete(
            Long id,
            String role
    ) {

        validateAdmin(role);

        ProductData data =
                repository.findById(id)
                        .orElseThrow(
                                () ->
                                new ResourceNotFoundException(
                                        "Product Not Found"
                                )
                        );

        repository.delete(data);

        elasticRepository.deleteById(
                String.valueOf(id)
        );
    }

    @Override
    public ProductResponse getById(
            Long id
    ) {

        ProductData data =
                repository.findById(id)
                        .orElseThrow(
                                () ->
                                new ResourceNotFoundException(
                                        "Product Not Found"
                                )
                        );

        return map(data);
    }

    @Override
    public List<ProductResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    private void validateAdmin(
            String role
    ) {

        if (!Role.ADMIN.name()
                .equalsIgnoreCase(role)) {

            throw new UnauthorizedException(
                    "Only Admin Can Perform This Operation"
            );
        }
    }

    private void syncElastic(
            ProductData product
    ) {

        ProductDocument doc =
                ProductDocument.builder()
                        .id(
                                String.valueOf(
                                        product.getId()
                                )
                        )
                        .productCode(
                                product.getProductCode()
                        )
                        .name(
                                product.getName()
                        )
                        .category(
                                product.getCategory()
                        )
                        .quantity(
                                product.getQuantity()
                        )
                        .price(
                                product.getPrice()
                        )
                        .build();

        elasticRepository.save(doc);
    }

    private ProductResponse map(
            ProductData data
    ) {

        return ProductResponse.builder()
                .id(data.getId())
                .productCode(
                        data.getProductCode()
                )
                .name(data.getName())
                .category(
                        data.getCategory()
                )
                .quantity(
                        data.getQuantity()
                )
                .price(
                        data.getPrice()
                )
                .build();
    }
}