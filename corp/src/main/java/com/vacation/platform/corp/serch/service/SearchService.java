package com.vacation.platform.corp.serch.service;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    StayFinderResponseDTO<?> searchByQuery(String query);

}
