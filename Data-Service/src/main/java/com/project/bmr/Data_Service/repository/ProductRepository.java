package com.project.bmr.Data_Service.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.bmr.Data_Service.dto.CategoryCount;
import com.project.bmr.Data_Service.entity.ProductData;


public interface ProductRepository
        extends JpaRepository<ProductData, Long> {
	
	Optional<ProductData> findByProductCode(
            String productCode
    );

    boolean existsByProductCode(
            String productCode
    );
    long countByQuantityLessThan(
            Integer quantity
    );
    
    @Query("""
    		SELECT p.category as category,
    		COUNT(p) as total
    		FROM ProductData p
    		GROUP BY p.category
    		""")
    		List<CategoryCount> categoryStats();
}