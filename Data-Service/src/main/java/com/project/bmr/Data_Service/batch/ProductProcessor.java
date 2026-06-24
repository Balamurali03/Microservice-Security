package com.project.bmr.Data_Service.batch;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductProcessor
        implements ItemProcessor<
        ExcelProduct,
        ExcelProduct> {

    @Override
    public ExcelProduct process(
            ExcelProduct item
    ) {

        if (item.getProductCode() == null) {
            return null;
        }

        if (item.getName() == null) {
            return null;
        }

        if (item.getQuantity() < 0) {
            return null;
        }

        return item;
    }
}