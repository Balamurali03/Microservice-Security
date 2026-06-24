package com.project.bmr.Data_Service.service;


import java.util.List;

import com.project.bmr.Data_Service.dto.SearchResponse;


public interface SearchService {

    List<SearchResponse> search(
            String keyword
    );

    List<SearchResponse> category(
            String category
    );
}