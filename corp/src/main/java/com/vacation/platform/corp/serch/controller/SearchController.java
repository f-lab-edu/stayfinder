package com.vacation.platform.corp.serch.controller;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.serch.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SearchController {

    private  final SearchService searchService;

    @GetMapping("/search")
    public StayFinderResponseDTO<?> searchByQuery(@RequestParam String query) {
        return searchService.searchByQuery(query);
    }
}