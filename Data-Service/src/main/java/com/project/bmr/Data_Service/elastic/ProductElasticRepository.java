package com.project.bmr.Data_Service.elastic;


import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductElasticRepository
        extends ElasticsearchRepository<ProductDocument, String> {
	
	List<ProductDocument> findByNameContaining(
            String keyword
    );

    List<ProductDocument> findByCategory(
            String category
    );
}