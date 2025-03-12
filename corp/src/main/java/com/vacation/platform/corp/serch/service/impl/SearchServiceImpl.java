package com.vacation.platform.corp.serch.service.impl;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.serch.dto.SearchResponseDTO;
import com.vacation.platform.corp.serch.repository.support.SearchRepositorySupport;
import com.vacation.platform.corp.serch.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepositorySupport  searchRepositorySupport;

    @Override
    public StayFinderResponseDTO<?> searchByQuery(String query) {
        List<SearchResponseDTO> resultList = searchRepositorySupport.searchAccommodation(query);

	    return StayFinderResponseDTO.success(resultList);
    }
}
