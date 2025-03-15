package com.vacation.platform.corp.serch.service.impl;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.serch.repository.support.SearchRepositorySupport;
import com.vacation.platform.corp.serch.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepositorySupport  searchRepositorySupport;

    @Override
    public StayFinderResponseDTO<?> searchByQuery(String location, LocalDate startDate, LocalDate endDate, int capacity) {
        List<?> resultList = searchRepositorySupport.searchAccommodation(location, startDate, endDate, capacity);

	    return StayFinderResponseDTO.success(resultList);
    }
}
