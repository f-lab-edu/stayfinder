package com.vacation.platform.corp.serch.service;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface SearchService {

    StayFinderResponseDTO<?> searchByQuery(String location, LocalDate startDate, LocalDate endDate, int capacity);

}
