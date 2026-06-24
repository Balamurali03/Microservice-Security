package com.project.bmr.Data_Service.batch;


import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcelProduct {

    private String productCode;

    private String name;

    private String category;

    private Integer quantity;

    private BigDecimal price;
}