package com.project.bmr.Data_Service.dto;


import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private String productCode;

    private String name;

    private String category;

    private Integer quantity;

    private BigDecimal price;
}