package com.vacation.platform.stayfinder.terms.service;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TermsService {

    StayFinderResponseDTO<List<TermsDto.MainResponseDto>> getTermsMain();

    StayFinderResponseDTO<List<TermsDto.SubResponseDto>> getTermsSub(TermsDto termsDto);

    void registerTerms (TermsDto termsDto);

}
