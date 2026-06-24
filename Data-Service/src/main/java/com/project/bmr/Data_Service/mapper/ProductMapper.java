package com.project.bmr.Data_Service.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.project.bmr.Data_Service.batch.ExcelProduct;
import com.project.bmr.Data_Service.elastic.ProductDocument;
import com.project.bmr.Data_Service.entity.ProductData;

@Component
public class ProductMapper {

    public ProductDocument toDocument(
            ProductData entity
    ) {

        return ProductDocument.builder()
                .id(entity.getId().toString())
                .productCode(entity.getProductCode())
                .name(entity.getName())
                .category(entity.getCategory())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .build();
    }

    public ProductData toEntity(
            ExcelProduct excel
    ) {

        return ProductData.builder()
                .productCode(excel.getProductCode())
                .name(excel.getName())
                .category(excel.getCategory())
                .quantity(excel.getQuantity())
                .price(excel.getPrice())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}