package com.vacation.platform.stayfinder.serch.service.impl;

import com.vacation.platform.stayfinder.serch.repository.support.SearchRepositorySupport;
import com.vacation.platform.stayfinder.serch.service.SearchService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepositorySupport  searchRepositorySupport;

    @Override
    public StayFinderResponseDTO<?> searchByQuery(String query) {
        return StayFinderResponseDTO.success();
    }
}
