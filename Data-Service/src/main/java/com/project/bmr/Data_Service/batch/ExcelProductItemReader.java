package com.project.bmr.Data_Service.batch;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class ExcelProductItemReader
        implements ItemReader<ExcelProduct> {

    private Iterator<ExcelProduct> iterator;

    public void setProducts(
            List<ExcelProduct> products
    ) {

        this.iterator =
                products.iterator();
    }

    @Override
    public ExcelProduct read() {

        if (iterator != null &&
                iterator.hasNext()) {

            return iterator.next();
        }

        return null;
    }
}