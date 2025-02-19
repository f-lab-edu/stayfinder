package com.vacation.platform.stayfinder.serch.service;

import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    StayFinderResponseDTO<?> searchByQuery(String query);

}
