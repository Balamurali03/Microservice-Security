package com.project.bmr.Data_Service.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bmr.Data_Service.dto.SearchResponse;
import com.project.bmr.Data_Service.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<SearchResponse>>
    search(

            @RequestParam
            String keyword
    ) {

        return ResponseEntity.ok(
                searchService.search(
                        keyword
                )
        );
    }

    @GetMapping("/category")
    public ResponseEntity<List<SearchResponse>>
    category(

            @RequestParam
            String category
    ) {

        return ResponseEntity.ok(
                searchService.category(
                        category
                )
        );
    }
}