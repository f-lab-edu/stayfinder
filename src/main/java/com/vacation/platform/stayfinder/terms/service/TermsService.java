package com.vacation.platform.stayfinder.terms.service;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TermsService {

    StayFinderResponseDTO<List<Terms>> getTermsMain();

    StayFinderResponseDTO<List<TermsSub>> getTermsSub(TermsDto termsDto);

    void registerTerms (TermsDto termsDto);

}
