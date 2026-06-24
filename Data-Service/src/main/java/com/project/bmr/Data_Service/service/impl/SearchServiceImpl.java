package com.project.bmr.Data_Service.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.project.bmr.Data_Service.dto.SearchResponse;
import com.project.bmr.Data_Service.elastic.ProductDocument;
import com.project.bmr.Data_Service.elastic.ProductElasticRepository;
import com.project.bmr.Data_Service.service.SearchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl
        implements SearchService {

    private final ProductElasticRepository repository;

    @Override
    public List<SearchResponse> search(
            String keyword
    ) {

        return repository
                .findByNameContaining(
                        keyword
                )
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<SearchResponse> category(
            String category
    ) {

        return repository
                .findByCategory(
                        category
                )
                .stream()
                .map(this::map)
                .toList();
    }

    private SearchResponse map(
            ProductDocument doc
    ) {

        return SearchResponse.builder()
                .id(doc.getId())
                .productCode(
                        doc.getProductCode()
                )
                .name(doc.getName())
                .category(
                        doc.getCategory()
                )
                .quantity(
                        doc.getQuantity()
                )
                .price(
                        doc.getPrice()
                )
                .build();
    }
}