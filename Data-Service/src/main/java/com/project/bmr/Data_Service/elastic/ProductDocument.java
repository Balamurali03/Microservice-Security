package com.project.bmr.Data_Service.elastic;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "products")
public class ProductDocument {

    @Id
    private String id;

    private String productCode;

    private String name;

    private String category;

    private Integer quantity;

    private BigDecimal price;
}