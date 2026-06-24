package com.project.bmr.Data_Service.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(
        basePackages =
                "com.project.bmr.Data_Service.elastic"
)
public class ElasticsearchConfig {
}
