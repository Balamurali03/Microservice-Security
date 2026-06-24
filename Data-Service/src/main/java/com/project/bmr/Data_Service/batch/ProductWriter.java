package com.project.bmr.Data_Service.batch;


import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.project.bmr.Data_Service.elastic.ProductDocument;
import com.project.bmr.Data_Service.elastic.ProductElasticRepository;
import com.project.bmr.Data_Service.entity.ProductData;
import com.project.bmr.Data_Service.mapper.ProductMapper;
import com.project.bmr.Data_Service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductWriter
        implements ItemWriter<ExcelProduct> {

    private final ProductRepository repository;
    
    private final ProductMapper productMapper;
    
    private final ProductElasticRepository
    elasticRepository;

    @Override
    public void write(
            Chunk<? extends ExcelProduct> chunk
    ) {

    	List<ProductData> entities =
                chunk.getItems()
                        .stream()
                        .map(productMapper::toEntity)
                        .toList();

        List<ProductData> savedProducts =
                repository.saveAll(entities);

        List<ProductDocument> documents =
                savedProducts.stream()
                        .map(productMapper::toDocument)
                        .toList();

        elasticRepository.saveAll(documents);
    }
}
