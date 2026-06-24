package com.project.bmr.Data_Service.batch;


import java.util.List;

import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;

import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    public Job importProductJob(

            JobRepository jobRepository,

            Step productStep
    ) {

        return new JobBuilder(
                "importProductJob",
                jobRepository
        )
                .start(productStep)
                .build();
    }

    @Bean
    public Step productStep(

            JobRepository jobRepository,

            PlatformTransactionManager txManager,

            ExcelProductItemReader reader,

            ProductProcessor processor,

            ProductWriter writer
    ) {

        return new StepBuilder(
                "productStep",
                jobRepository
        )
                .<ExcelProduct, ExcelProduct>
                        chunk(
                                1000,
                                txManager
                        )
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}