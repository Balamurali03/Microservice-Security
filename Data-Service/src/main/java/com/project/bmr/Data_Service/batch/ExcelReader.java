package com.project.bmr.Data_Service.batch;


import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

@Component
public class ExcelReader {

    public List<ExcelProduct> read(
            InputStream inputStream
    ) throws Exception {

        Workbook workbook =
                WorkbookFactory.create(
                        inputStream
                );

        Sheet sheet =
                workbook.getSheetAt(0);

        List<ExcelProduct> products =
                new ArrayList<>();

        for (int i = 1;
             i <= sheet.getLastRowNum();
             i++) {

            Row row =
                    sheet.getRow(i);

            if (row == null) {
                continue;
            }

            ExcelProduct product =
                    ExcelProduct.builder()
                            .productCode(
                                    row.getCell(0)
                                            .getStringCellValue()
                            )
                            .name(
                                    row.getCell(1)
                                            .getStringCellValue()
                            )
                            .category(
                                    row.getCell(2)
                                            .getStringCellValue()
                            )
                            .quantity(
                                    (int) row.getCell(3)
                                            .getNumericCellValue()
                            )
                            .price(
                                    BigDecimal.valueOf(
                                            row.getCell(4)
                                                    .getNumericCellValue()
                                    )
                            )
                            .build();

            products.add(product);
        }

        workbook.close();

        return products;
    }
}
