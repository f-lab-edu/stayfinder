package com.vacation.platform.corp.serch.controller;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.serch.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SearchController {

    private  final SearchService searchService;

    @GetMapping("/search")
    public StayFinderResponseDTO<?> searchByQuery(@RequestParam String location,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate,
                                                  @RequestParam int capacity) {
        return searchService.searchByQuery(location,  startDate, endDate, capacity);
    }
}