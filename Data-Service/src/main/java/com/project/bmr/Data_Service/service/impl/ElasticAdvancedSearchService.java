package com.project.bmr.Data_Service.service.impl;


import org.springframework.stereotype.Service;

import com.project.bmr.Data_Service.elastic.ProductDocument;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ElasticAdvancedSearchService {

    private final ElasticsearchClient client;

    public SearchResponse<ProductDocument>
    fuzzySearch(
            String keyword
    ) throws Exception {

        return client.search(
                s -> s
                        .index("products")
                        .query(
                                q -> q.match(
                                        m -> m
                                                .field("name")
                                                .query(keyword)
                                                .fuzziness("AUTO")
                                )
                        ),
                ProductDocument.class
        );
    }
}